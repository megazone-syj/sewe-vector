package com.megazone.dx.ai;

import org.bytedeco.javacpp.PointerPointer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import scala.collection.Seq;

import com.megazone.dx.ai.SEWrapper;
import com.twitter.penguin.korean.KoreanTokenJava;
import com.twitter.penguin.korean.TwitterKoreanProcessor;
import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.phrase_extractor.KoreanPhraseExtractor;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer;

public class SeWePredict {

    private SEWrapper.FastTextApi sfta;
    private WEWrapper.FastTextApi wfta;

    public SeWePredict() {
        sfta = new SEWrapper.FastTextApi();
        wfta = new WEWrapper.FastTextApi();
    }

    public void runCmd(String[] args) {
        // Prepend "fasttext" to the argument list so that it is compatible with C++'s main()
        String[] cArgs = new String[args.length + 1];
        cArgs[0] = "fasttext";
        System.arraycopy(args, 0, cArgs, 1, args.length);
        wfta.runCmd(cArgs.length, new PointerPointer(cArgs));
    }
    public void srunCmd(String[] args) {
        // Prepend "fasttext" to the argument list so that it is compatible with C++'s main()
        String[] cArgs = new String[args.length + 1];
        cArgs[0] = "fasttext";
        System.arraycopy(args, 0, cArgs, 1, args.length);
        sfta.runCmd(cArgs.length, new PointerPointer(cArgs));
    }
    public void sloadModel(String modelFile) throws Exception {
        if (!new File(modelFile).exists()) {
            throw new Exception("Model file doesn't exist!");
        }
        if (!sfta.checkModel(modelFile)) {
            throw new Exception("Model file's format is not compatible with this JFastText version!");
        }
        sfta.loadModel(modelFile);
    }
    public void wloadModel(String modelFile) throws Exception {
        if (!new File(modelFile).exists()) {
            throw new Exception("Model file doesn't exist!");
        }
        if (!wfta.checkModel(modelFile)) {
            throw new Exception("Model file's format is not compatible with this JFastText version!");
        }
        wfta.loadModel(modelFile);
    }
    public void sunloadModel() {
        sfta.unloadModel();
    }
    public void wunloadModel() {
        wfta.unloadModel();
    }
    public void test(String testFile, float threshold) {
        wtest(testFile, 1, threshold);
    }

    public void wtest(String testFile, int k, float threshold) {
        if (k <= 0) {
            throw new IllegalArgumentException("k must be positive");
        }
        wfta.test(testFile, k);
    }

    public String predict(String text, float threshold){
        List<String> predictions = predict(text, 1, threshold);
        return predictions.size() > 0? predictions.get(0): null;
    }

    public List<String> predict(String text, int k, float threshold) {
        if (k <= 0) {
            throw new IllegalArgumentException("k must be positive");
        }
        WEWrapper.StringVector sv = wfta.predict(text, k);
        List<String> predictions = new ArrayList<>();
        for (int i = 0; i < sv.size(); i++) {
            predictions.add(sv.get(i).getString());
        }
        return predictions;
    }

    public ProbLabel predictProba(String text, float threshold){
        List<ProbLabel> probaPredictions = predictProba(text, 1, threshold);
        return probaPredictions.size() > 0? probaPredictions.get(0): null;
    }

    public List<ProbLabel> predictProba(String rawtext, int k, float threshold) {
        if (k <= 0) {
            throw new IllegalArgumentException("k must be positive");
        }
        
        String inputText = "";
        rawtext = rawtext.replaceAll(",", "").replaceAll(".", "");
		// Normalize
		CharSequence normalized = TwitterKoreanProcessorJava.normalize(rawtext);
		// Tokenize
		Seq<KoreanTokenizer.KoreanToken> tokens = TwitterKoreanProcessorJava.tokenize(normalized);
		// Stemming
		Seq<KoreanTokenizer.KoreanToken> stemmed = TwitterKoreanProcessorJava.stem(tokens);
		List<KoreanTokenJava> tokenList = TwitterKoreanProcessorJava.tokensToJavaKoreanTokenList(stemmed);
		for (KoreanTokenJava token : tokenList)
			inputText = inputText +  token.getText() + token.getPos().name() + " ";
//		System.out.println(inputText);
		
		String result = "";
        WEWrapper.FloatStringPairVector fspv = wfta.predictProba(inputText, k);
        List<ProbLabel> probaPredictions = new ArrayList<>();
        for (int i = 0; i < fspv.size(); i++) {
            float logProb = fspv.first(i);
            String label = fspv.second(i).getString();
            probaPredictions.add(new ProbLabel(logProb, label));
        }
        return probaPredictions;
    }
    
    public List<ProbLabel> predictQAProba(String fileName,String rawtext, int k, float threshold) {
        if (k <= 0) {
            throw new IllegalArgumentException("k must be positive");
        }
        String inputText = "";
		// Normalize
		CharSequence normalized = TwitterKoreanProcessorJava.normalize(rawtext);
		// Tokenize
		Seq<KoreanTokenizer.KoreanToken> tokens = TwitterKoreanProcessorJava.tokenize(normalized);
		// Stemming
		Seq<KoreanTokenizer.KoreanToken> stemmed = TwitterKoreanProcessorJava.stem(tokens);
		List<KoreanTokenJava> tokenList = TwitterKoreanProcessorJava.tokensToJavaKoreanTokenList(stemmed);
		for (KoreanTokenJava token : tokenList)
			inputText = inputText +  token.getText() + token.getPos().name() + " ";
		System.out.println(inputText);
		
        WEWrapper.FloatStringPairVector fspv = wfta.nnSentProb(k, fileName,inputText);
        List<ProbLabel> probaPredictions = new ArrayList<>();
        for (int i = 0; i < fspv.size(); i++) {
            float qaProb = fspv.first(i);
            if(Float.compare(qaProb,threshold)<0) continue;
            String labeledQuestion = fspv.second(i).getString();
            String questionId = labeledQuestion.substring(labeledQuestion.lastIndexOf("__label__")+9, labeledQuestion.lastIndexOf("__label__")+27);
            probaPredictions.add(new ProbLabel(qaProb, questionId));
        }
        return probaPredictions;
    }
    
   
   
    public List<Float> getVector(String word) {
        WEWrapper.RealVector rv = wfta.getWordVector(word);
        List<Float> wordVec = new ArrayList<>();
        for (int i = 0; i < rv.size(); i++) {
            wordVec.add(rv.get(i));
        }
        return wordVec;
    }


    public String preprocess(String talkbotId, String dir, Map<String, List> labeldSentenceMap) {
		// This returns a JSON or XML with the users

		PrintWriter writer_ko;
		try {
			 writer_ko = new PrintWriter( dir +"/"+ talkbotId + "_labeled_data.txt");
			 for (Map.Entry<String, List> entry : labeldSentenceMap.entrySet()) {

				 String intentId = entry.getKey();
				 List<String> intentSentenceSet = entry.getValue(); 
				 for (String intentSentence : intentSentenceSet) {

					if (intentSentence == null || intentSentence.isEmpty()) continue;

					String result = "";

					// Normalize
					CharSequence normalized = TwitterKoreanProcessorJava.normalize(intentSentence);
					// Tokenize
					Seq<KoreanTokenizer.KoreanToken> tokens = TwitterKoreanProcessorJava.tokenize(normalized);
					// Stemming
					Seq<KoreanTokenizer.KoreanToken> stemmed = TwitterKoreanProcessorJava.stem(tokens);

					List<KoreanTokenJava> tokenList = TwitterKoreanProcessorJava.tokensToJavaKoreanTokenList(stemmed);
					for (KoreanTokenJava token : tokenList)
						result += token.getText()  + token.getPos().name() + " ";
					writer_ko.write("__label__" + intentId  + " " + result + "\n");

				}

			}

			writer_ko.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

		return "Preprocessing is finished successfully!";
    }
    public int getNWords() {
        return wfta.getNWords();
    }

    public List<String> getWords() {
        return stringVec2Strings(wfta.getWords());
    }

    public int getNLabels() {
        return wfta.getNLabels();
    }

    public List<String> getLabels() {
        return stringVec2Strings(wfta.getWords());
    }

    public double getLr() {
        return wfta.getLr();
    }

    public int getLrUpdateRate() {
        return wfta.getLrUpdateRate();
    }

    public int getDim() {
        return wfta.getDim();
    }

    public int getContextWindowSize() {
        return wfta.getContextWindowSize();
    }

    public int getEpoch() {
        return wfta.getEpoch();
    }

    public int getMinCount() {
        return wfta.getMinCount();
    }

    public int getMinCountLabel() {
        return wfta.getMinCountLabel();
    }

    public int getNSampledNegatives() {
        return wfta.getNSampledNegatives();
    }

    public int getWordNgrams() {
        return wfta.getWordNgrams();
    }

    public String getLossName() {
        return wfta.getLossName().getString();
    }

    public String getModelName() {
        return wfta.getModelName().getString();
    }

    public int getBucket() {
        return wfta.getBucket();
    }

    public int getMinn() {
        return wfta.getMinn();
    }

    public int getMaxn() {
        return wfta.getMaxn();
    }

    public double getSamplingThreshold() {
        return wfta.getSamplingThreshold();
    }

    public String getLabelPrefix() {
        return wfta.getLabelPrefix().getString();
    }

    public String getPretrainedVectorsFileName() {
        return wfta.getPretrainedVectorsFileName().getString();
    }

    private static List<String> stringVec2Strings(WEWrapper.StringVector sv) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < sv.size(); i++) {
            strings.add(sv.get(i).getString());
        }
        return strings;
    }

    public static class ProbLabel {
        public float logProb;
        public String label;
        public ProbLabel(float logProb, String label) {
            this.logProb = logProb;
            this.label = label;
        }
        @Override
        public String toString() {
            return String.format("logProb = %f, label = %s", logProb, label);
        }
    }

    public static void main(String[] args) {
        SeWePredict jft = new SeWePredict();
        jft.runCmd(args);
    }
}
