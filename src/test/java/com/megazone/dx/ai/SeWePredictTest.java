
package com.megazone.dx.ai;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.megazone.dx.ai.SeWePredict;
import com.twitter.penguin.korean.KoreanTokenJava;
import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer;
import com.twitter.penguin.korean.tokenizer.Sentence;

import scala.collection.Seq;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeWePredictTest {

//	 @Test
//	 public void test01TrainSupervisedCmd() {
//	 System.out.printf("\nTraining supervised model ...\n");
//	 SeWePredict jft = new SeWePredict();
//	 jft.runCmd(new String[] {
//	 "supervised",
//	 "-input", "src/test/resources/data/labeled_data.txt",
//	 "-output", "src/test/resources/models/supervised.model",
//	 "-lr", "1.0",
//	 "-epoch", "25",
//	 "-wordNgrams", "2",
//	 "-bucket", "4000000"
//	 });
//	 }
//	
//	 @Test
//	 public void test02TrainSkipgramCmd() {
//	 System.out.printf("\nTraining skipgram word-embedding ...\n");
//	 SeWePredict jft = new SeWePredict();
//	 jft.runCmd(new String[] {
//	 "skipgram",
//	 "-input", "src/test/resources/data/unlabeled_data.txt",
//	 "-output", "src/test/resources/models/skipgram.model",
//	 "-bucket", "100",
//	 "-minCount", "1",
//	
//	 });
//	 }
//	
//	 @Test
//	 public void test03TrainCbowCmd() {
//	 System.out.printf("\nTraining cbow word-embedding ...\n");
//	 SeWePredict jft = new SeWePredict();
//	 jft.runCmd(new String[] {
//	 "skipgram",
//	 "-input", "src/test/resources/data/unlabeled_data.txt",
//	 "-output", "src/test/resources/models/cbow.model",
//	 "-bucket", "100",
//	 "-minCount", "1"
//	 });
//	 }
//	
//	 @Test
//	 public void test04Predict() throws Exception {
//	 SeWePredict jft = new SeWePredict();
//	 jft.wloadModel("src/test/resources/models/supervised.model.bin");
//	 String text = "개명/Noun 을/Josa 하다/Verb 경우/Noun 합격/Noun 증명서/Noun 랑/Josa 성적/Noun
//	 증명서/Noun 제출/Noun 하다/Verb 때/Noun 주민등록/Noun 등본/Noun 초본/Noun 제출/Noun 하다/Verb
//	 되다/Verb 요/Noun ?/Punctuation";
//	 String predictedLabel = jft.predict(text, 0.8f);
//	 System.out.printf("\nText: '%s', label: '%s'\n", text, predictedLabel);
//	 }
	
//	 @Test
	// public void test05PredictProba() throws Exception {
	// SeWePredict jft = new SeWePredict();
	// jft.wloadModel("src/test/resources/models/supervised.model.bin");
	// String text = "학기/Noun 중/Suffix 에/Josa 간호/Noun 과로/Noun 편입/Noun 하다/Verb
	// 있다/Adjective ?/Punctuation";
	// SeWePredict.ProbLabel predictedProbLabel = jft.predictProba(text, 0.8f);
	//// System.out.printf("\nText: '%s', label: '%s', probability: %f\n",
	//// text, predictedProbLabel.label, Math.exp(predictedProbLabel.logProb));
	// }
	//
	// @Test
	// public void test06MultiPredictProba() throws Exception {
	// SeWePredict jft = new SeWePredict();
	// jft.wloadModel("src/test/resources/models/supervised.model.bin");
	// String text = " 고등학교/Noun 전공/Noun 과/Josa 상관없다/Adjective 모든/Noun 과/Noun 에/Josa
	// 지원/Noun 하다/Verb 수/Noun 있다/Adjective 가요/Noun ?/Punctuation";
	// System.out.printf("\nText: '%s'\n", text);
	// for (SeWePredict.ProbLabel predictedProbLabel: jft.predictProba(text, 3,
	// 0.8f)) {
	// System.out.printf("\tlabel: '%s', probability: %f\n",
	// predictedProbLabel.label, Math.exp(predictedProbLabel.logProb));
	// }
	// }
	//
	// @Test
	// public void test07GetVector() throws Exception {
	// SeWePredict jft = new SeWePredict();
	// jft.wloadModel("src/test/resources/models/supervised.model.bin");
	// String word = "soccer";
	// List<Float> vec = jft.getVector(word);
	// System.out.printf("\nWord embedding vector of '%s': %s\n", word, vec);
	// }
	//
	// /**
	// * Test retrieving model's information: words, labels, learning rate, etc.
	// */
	// @Test
	// public void test08ModelInfo() throws Exception {
	// System.out.printf("\nSupervised model information:\n");
	// SeWePredict jft = new SeWePredict();
	// jft.wloadModel("src/test/resources/models/supervised.model.bin");
	// System.out.printf("\tnumber of words = %d\n", jft.getNWords());
	// System.out.printf("\twords = %s\n", jft.getWords());
	// System.out.printf("\tlearning rate = %g\n", jft.getLr());
	// System.out.printf("\tdimension = %d\n", jft.getDim());
	// System.out.printf("\tcontext window size = %d\n",
	// jft.getContextWindowSize());
	// System.out.printf("\tepoch = %d\n", jft.getEpoch());
	// System.out.printf("\tnumber of sampled negatives = %d\n",
	// jft.getNSampledNegatives());
	// System.out.printf("\tword ngrams = %d\n", jft.getWordNgrams());
	// System.out.printf("\tloss name = %s\n", jft.getLossName());
	// System.out.printf("\tmodel name = %s\n", jft.getModelName());
	// System.out.printf("\tnumber of buckets = %d\n", jft.getBucket());
	// System.out.printf("\tlabel prefix = %s\n\n", jft.getLabelPrefix());
	// }
	//
	// /**
	// * Test model unloading to release memory (Java's GC doesn't collect memory
	// * allocated by native function calls).
	// */
	// @Test
	// public void test09ModelUnloading() throws Exception {
	// SeWePredict jft = new SeWePredict();
	// System.out.println("\nLoading model ...");
	// jft.wloadModel("src/test/resources/models/supervised.model.bin");
	// System.out.println("Unloading model ...");
	// jft.wunloadModel();
	// }

	// @Test
	// public void test10nnSent() throws Exception {
	// SeWePredict jft = new SeWePredict();
	// System.out.println("\nLoading model ...");
	// jft.sloadModel("/home/adam-talk/apiServer/intent/model/wiki_model.bin");
	// String text = "52루4989 차량의 7월 3일부터 9월 30일까지 미납 요금이력 조회하고 싶어";
	// String fileName =
	// "/home/adam-talk/apiServer/intent/data/a8c98184-231b-4e03-a0f2-925f6bafd2db_labeled_data.txt";
	// System.out.println("\nPredict sentences ...");
	// for (SeWePredict.ProbLabel predictedProbLabel:
	// jft.predictIntentProba(fileName,text, 5)) {
	// System.out.printf("\tsentence: '%s', probability: %f\n",
	// predictedProbLabel.label, predictedProbLabel.logProb);
	// }
	// text = "1종차량을 타고 서울에서 동수원까지 이동하는데 드는 비용은 얼마야";
	// System.out.println("\nAgain predict sentences ...");
	// for (SeWePredict.ProbLabel predictedProbLabel:
	// jft.predictIntentProba(fileName,text, 5)) {
	// System.out.printf("\tsentence: '%s', probability: %f\n",
	// predictedProbLabel.label, predictedProbLabel.logProb);
	// }
	// }
//	@Test
//	public void test12evalutetw() throws Exception {
//		String SAMPLE_XLSX_FILE_PATH = "/home/classfier/sewe-master/src/test/resources/data/labs.xlsx";
//		PrintWriter writer_main = new PrintWriter("/home/classfier/sewe-master/src/test/resources/data/data_main_tw.txt");
//		PrintWriter writer_para = new PrintWriter("/home/classfier/sewe-master/src/test/resources/data/data_para_tw.txt");
//		PrintWriter writer_total = new PrintWriter("/home/classfier/sewe-master/src/test/resources/data/data_total_tw.txt");
//		Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));
//		Sheet sheet = workbook.getSheetAt(0);
//
//		// Create a DataFormatter to format and get each cell's value as String
//		DataFormatter dataFormatter = new DataFormatter();
//		// 1. You can obtain a rowIterator and columnIterator and iterate over them
//		// System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
//		Iterator<Row> rowIterator = sheet.rowIterator();
//		int k = 0;
//		String mainNum = "";
//		int i = 0;
//		while (rowIterator.hasNext()) {
//			if (i > 4040)
//				break;
//			i++;
//			Row row = rowIterator.next();
//
//			// Now let's iterate over the columns of the current row
//			Iterator<Cell> cellIterator = row.cellIterator();
//			k = 0;
//			String rowNum = "";
//
//			String mainOrPara = "";
//			String line = "";
//			while (cellIterator.hasNext()) {
//				Cell cell = cellIterator.next();
//				String cellValue = dataFormatter.formatCellValue(cell);
//				cellValue = cellValue.replaceAll("[\\-\\+\\.\\?\\(\\)\\}\\@\\#\\{\\$^:,]","");
//				if (k == 0)
//					rowNum = cellValue;
//				if (k == 2) {
//					mainOrPara = cellValue;
//					if ("P0".equals(cellValue))
//						mainNum = rowNum;
//				}
//				if (k == 4) {
//					// Normalize
//					CharSequence normalized = TwitterKoreanProcessorJava.normalize(cellValue);
//					// Tokenize
//					Seq<KoreanTokenizer.KoreanToken> tokens = TwitterKoreanProcessorJava.tokenize(normalized);
//					// Stemming
//					Seq<KoreanTokenizer.KoreanToken> stemmed = TwitterKoreanProcessorJava.stem(tokens);
//	
//					List<KoreanTokenJava> tokenList = TwitterKoreanProcessorJava.tokensToJavaKoreanTokenList(stemmed);
//					for (KoreanTokenJava token : tokenList)
//						line += token.getText() + token.getPos().name() + " ";
//					if ("P0".equals(mainOrPara))
//						writer_main.write("__label__" + org.apache.commons.lang3.StringUtils.leftPad(rowNum, 18, "0")
//								+ " " + line + "\n");
//					else
//						writer_para.write("__label__" + mainNum + "__" + rowNum + " " + cellValue + "\n");
//					
//					writer_total.write(line + "\n");
//				}
//
//				k++;
//			}
//			// System.out.print("__label__" + rowNum + " " + line);
//
//		}
//		writer_main.close();
//		writer_para.close();
//		writer_total.close();
//		workbook.close();
//	}
//	@Test
//	public void test12evalute() throws Exception {
//		String SAMPLE_XLSX_FILE_PATH = "/home/classfier/sewe-master/src/test/resources/data/labs.xlsx";
//		PrintWriter writer_main = new PrintWriter("/home/classfier/sewe-master/src/test/resources/data/data_main_lea.txt");
//		PrintWriter writer_para = new PrintWriter("/home/classfier/sewe-master/src/test/resources/data/data_para_lea.txt");
//		PrintWriter writer_total = new PrintWriter("/home/classfier/sewe-master/src/test/resources/data/data_total_lea.txt");
//		Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));
//		Sheet sheet = workbook.getSheetAt(0);
//
//		// Create a DataFormatter to format and get each cell's value as String
//		DataFormatter dataFormatter = new DataFormatter();
//		// 1. You can obtain a rowIterator and columnIterator and iterate over them
//		// System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
//		Iterator<Row> rowIterator = sheet.rowIterator();
//		int k = 0;
//		String mainNum = "";
//		int i = 0;
//		PosTagger tagger = PosTaggerSession.getInstance();
//		while (rowIterator.hasNext()) {
//			if (i > 4040)
//				break;
//			i++;
//			Row row = rowIterator.next();
//
//			// Now let's iterate over the columns of the current row
//			Iterator<Cell> cellIterator = row.cellIterator();
//			k = 0;
//			String rowNum = "";
//
//			String mainOrPara = "";
//			String line = "";
//			while (cellIterator.hasNext()) {
//				Cell cell = cellIterator.next();
//				String cellValue = dataFormatter.formatCellValue(cell);
//				cellValue = cellValue.replaceAll("[\\-\\+\\.\\?\\(\\)\\}\\@\\#\\{\\$^:,]","");
//				if (k == 0)
//					rowNum = cellValue;
//				if (k == 2) {
//					mainOrPara = cellValue;
//					if ("P0".equals(cellValue))
//						mainNum = rowNum;
//				}
//				if (k == 4) {
//					Analysis anal = tagger.getTaggedObject(cellValue);
//					List<com.knu.lea.api.util.Tag.Sentence> splitedList = anal.sentence;
//
//					for(com.knu.lea.api.util.Tag.Sentence sent: splitedList) {
//					  
//						   line = restoredVerbSentence(sent);	
//					}
//					
//					if ("P0".equals(mainOrPara))
//						writer_main.write("__label__" + org.apache.commons.lang3.StringUtils.leftPad(rowNum, 18, "0")
//								+ " " + line + "\n");
//					else
//						writer_para.write("__label__" + mainNum + "__" + rowNum + " " + cellValue + "\n");
//					
//					writer_total.write(cellValue + "\n");
//				}
//
//				k++;
//			}
//			// System.out.print("__label__" + rowNum + " " + line);
//
//		}
//		writer_main.close();
//		writer_para.close();
//		writer_total.close();
//		workbook.close();
//	}


	@Test
	public void test10nnSent() throws Exception {

		BufferedReader reader;

		PrintWriter writer_matched = new PrintWriter(
				"/home/classfier/sewe-master/data/data_match_lea_50898.txt");
		PrintWriter writer_mismatch = new PrintWriter(
				"/home/classfier/sewe-master/data/data_mismatch_lea_50898.txt");
		File file = new File("/home/classfier/sewe-master/data/data_para_lea_50898.txt");
		// File file = new File("C:\\newworkspace\\sewe-master\\data_para.txt");

		reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		SeWePredict jft = new SeWePredict();
		System.out.println("\nLoading model ...");
		jft.wloadModel("/home/classfier/new_irq_news_wiki_100_300_bucket2000000v2.bin");
//		jft.sloadModel("/home/adam-talk/classifier/intent/model/sent2vec_model.bin");
		String fileName = "/home/classfier/sewe-master/data/data_main_lea_50898.txt";
		System.out.println("\nPredict sentences ...");
		int i = 0;
		int k = 0;
		while (true) {

			if (i > 50898) break;
//			if (i > 3030) break;
			final String line = reader.readLine();
			if (line == null)
				break;
			if (StringUtils.isAllEmpty(line))
				continue;

			// System.out.printf(line.substring(line.indexOf(' ') + 1)+"\n");

			String text = line.substring(line.indexOf(" ") + 1).replaceAll("'", "");
//			System.out.printf("input: "+text);
			for (SeWePredict.ProbLabel predictedProbLabel : jft.predictQAProba(fileName, text, 1, 0.0f)) {
				String compareWith = line.substring(9, line.indexOf(" "));
				compareWith = compareWith.substring(0, compareWith.indexOf("_"));
//				System.out.printf("\tsentence: '%s'\n '%s'__ probability: %f\n",text, predictedProbLabel.label,predictedProbLabel.logProb);
				if (Integer.parseInt(predictedProbLabel.label) == Integer.parseInt(compareWith)) {
					writer_matched.write(Integer.parseInt(predictedProbLabel.label) + "__" + predictedProbLabel.logProb
							+ "__" + line + "\n");
					k++;
				} else {
					writer_mismatch.write(Integer.parseInt(predictedProbLabel.label) + "__" + predictedProbLabel.logProb
							+ "__" + line + "\n");
				}
			}

			i++;
		}
		System.out.printf(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>MatchCount/TotalCount: %d/%d", k, i);
		reader.close();
		writer_matched.close();
		writer_mismatch.close();
	}
//	@Test
//	public void test10nnSent() throws Exception {
//
//		BufferedReader reader;
//
//		PrintWriter writer_matched = new PrintWriter(
//				"/home/classfier/sewe-master/src/test/resources/data/data_match_tw.txt");
//		PrintWriter writer_mismatch = new PrintWriter(
//				"/home/classfier/sewe-master/src/test/resources/data/data_mismatch_tw.txt");
//		File file = new File("/home/classfier/sewe-master/src/test/resources/data/data_para_tw.txt");
//		// File file = new File("C:\\newworkspace\\sewe-master\\data_para.txt");
//
//		reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//		SeWePredict jft = new SeWePredict();
//		System.out.println("\nLoading model ...");
//		jft.wloadModel("/home/classfier/sent2vec/wiki_model_new.tw.bin");
////		jft.sloadModel("/home/adam-talk/classifier/intent/model/sent2vec_model.bin");
//		String fileName = "/home/classfier/sewe-master/src/test/resources/data/data_main_tw.txt";
//		System.out.println("\nPredict sentences ...");
//		int i = 0;
//		int k = 0;
//		while (true) {
//			if (i > 50898)
////				if (i > 5000)
//				break;
//			final String line = reader.readLine();
//			if (line == null)
//				break;
//			if (StringUtils.isAllEmpty(line))
//				continue;
//
//			// System.out.printf(line.substring(line.indexOf(' ') + 1)+"\n");
//
//			String text = line.substring(line.indexOf(' ') + 1);
//
//			for (SeWePredict.ProbLabel predictedProbLabel : jft.predictQAProba(fileName, text, 1, 0.0f)) {
//				String compareWith = line.substring(9, line.indexOf(' '));
//				compareWith = compareWith.substring(0, compareWith.indexOf('_'));
////				System.out.printf("\tsentence: '%s'\n '%s'__ probability: %f\n",text, predictedProbLabel.label,predictedProbLabel.logProb);
//				if (Integer.parseInt(predictedProbLabel.label) == Integer.parseInt(compareWith)) {
//					writer_matched.write(Integer.parseInt(predictedProbLabel.label) + "__" + predictedProbLabel.logProb
//							+ "__" + line + "\n");
//					k++;
//				} else {
//					writer_mismatch.write(Integer.parseInt(predictedProbLabel.label) + "__" + predictedProbLabel.logProb
//							+ "__" + line + "\n");
//
//				}
//			}
//
//			i++;
//		}
//		System.out.printf(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>MatchCount/TotalCount: %d/%d", k, i);
//		reader.close();
//		writer_matched.close();
//		writer_mismatch.close();
//	}

//	@Test
//	public  void accessTestMorph() throws Exception {
//		
//		PosTagger tagger = new PosTagger();
//		tagger.init("C:/newworkspace/sewe-master/data/rsc", EnvPos.class);
////		tagger.init("/home/classfier/sewe-master/data/rsc", EnvPos.class);
//		
//		Analysis anal = tagger.getTaggedObject("오늘 하루도 즐겁게 지내자. 성내지 말고");
////		System.out.println(JsonUtil.print(anal));
//		 for (com.knu.lea.api.util.Tag.Sentence sent : anal.sentence) {
//			 for (Morph morph : sent.morp) {
//			 System.out.println(morph.id + "\t" + morph.lemma + "\t" + morph.type);
//		    }
//		 }	 
//			 anal = tagger.getTaggedObject("이럴경우 Root 비밀번호 입력 후 (Repair filesystem):e2fsck /dev/sdb1 입력 및 리부팅 해주면 정상부팅이 됩니다.");
////				System.out.println(JsonUtil.print(anal));
//				 for (com.knu.lea.api.util.Tag.Sentence sent : anal.sentence) {
//					 for (Morph morph : sent.morp) {
//					 System.out.println(morph.id + "\t" + morph.lemma + "\t" + morph.type);
//				    }
//	    }
//		
//
//		tagger.close();
//		
//		 }
	@Test
	public void makeWikiData() {

			BufferedReader reader;
			PrintWriter writer_ko;
			try {
				writer_ko = new PrintWriter("/home/classfier/sewe-master/src/test/resources/data/data_ko_main_twitter.txt");
				File file = new File("/home/classfier/sewe-master/src/test/resources/data/final.xml");

				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				while (true) {
					final String line = reader.readLine();
					if (line == null)
						break;
					if(StringUtils.isEmpty(line)|| !line.contains(".") || line.startsWith("<doc")|| line.startsWith("</doc>")) continue;

					List<Sentence> splitedList = TwitterKoreanProcessorJava.splitSentences(line);
					String result = "";
					for(Sentence sSentence: splitedList) {
						String sentence = sSentence.text();
						if(StringUtils.isEmpty(sentence)) continue;
						result = "";
						// Normalize
						CharSequence normalized = TwitterKoreanProcessorJava.normalize(sentence);

						// Tokenize
						Seq<KoreanTokenizer.KoreanToken> tokens = TwitterKoreanProcessorJava.tokenize(normalized);
						// Stemming
						Seq<KoreanTokenizer.KoreanToken> stemmed = TwitterKoreanProcessorJava.stem(tokens);
//						String result = String.join(" ", TwitterKoreanProcessorJava.tokensToJavaStringList(stemmed));
						List<KoreanTokenJava> tokenList = TwitterKoreanProcessorJava.tokensToJavaKoreanTokenList(stemmed);
						for(KoreanTokenJava token: tokenList)
							result = result + token.getText() + token.getPos().name()+" ";													
						if(StringUtils.isEmpty(result)) continue;
						result = result.replaceAll(" .Punctuation", "");
						writer_ko.write( result + "\n");						
						System.out.println(result);
					}

				}
				reader.close();
				writer_ko.close();

			} catch (Exception e) {

				e.printStackTrace();
		 	}
	}	
	
//	
//		@Test
//		public void MakeWikiDataTest() {
//				BufferedReader reader;
//				PrintWriter writer_ko;
//				try {
//					writer_ko = new PrintWriter("/home/albert-deep/mrc/GloVe/corpus_20190422.txt");
//					File file = new File("/home/albert-deep/mrc/GloVe/cc.ko.300.vec");
//					reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//					while (true) {
//						final String line = reader.readLine();
//						if (line == null) break;
//						System.out.println(line);
//						String[] phrases = line.split(" ");
//						writer_ko.write( phrases[0] + " ");						
//					}
//					reader.close();
//					writer_ko.close();
//
//				} catch (IOException e) {
//
//					e.printStackTrace();
//					
//			 	}
//		}
//
//	@Test
//	public void makeWikiData() {
//
//			BufferedReader reader;
//			PrintWriter writer_ko;
//			PrintWriter writer_ko_tag;
//			try {
//				writer_ko = new PrintWriter("/home/albert-deep/mrc/Easy-Namuwiki-Extractor/namuwiki_ko.txt");
//				writer_ko_tag = new PrintWriter("/home/albert-deep/mrc/Easy-Namuwiki-Extractor/namuwiki_ko_tag.txt");
//				File file = new File("/home/albert-deep/mrc/Easy-Namuwiki-Extractor/namuwiki_20190312.txt");
//
//				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//				while (true) {
//					final String line = reader.readLine();
//					if (line == null)
//						break;
//					if(StringUtils.isEmpty(line)|| !line.contains(".") || line.startsWith("<doc")|| line.startsWith("</doc>")) continue;
//
//					List<Sentence> splitedList = TwitterKoreanProcessorJava.splitSentences(line);
//					String result = "";
//					for(Sentence sSentence: splitedList) {
//						String sentence = sSentence.text();
//						if(StringUtils.isEmpty(sentence)) continue;
//						result = "";
//						// Normalize
//						CharSequence normalized = TwitterKoreanProcessorJava.normalize(sentence);
//
//						// Tokenize
//						Seq<KoreanTokenizer.KoreanToken> tokens = TwitterKoreanProcessorJava.tokenize(normalized);
//						// Stemming
//						Seq<KoreanTokenizer.KoreanToken> stemmed = TwitterKoreanProcessorJava.stem(tokens);
////						String result = String.join(" ", TwitterKoreanProcessorJava.tokensToJavaStringList(stemmed));
//						List<KoreanTokenJava> tokenList = TwitterKoreanProcessorJava.tokensToJavaKoreanTokenList(stemmed);
//						if(tokenList.size()<3) continue;
//						for(KoreanTokenJava token: tokenList)
//							result = result + token.getText() + token.getPos().name()+" ";													
//						if(StringUtils.isEmpty(result)) continue;
//						result = result.replaceAll(" .Punctuation", "");
//						writer_ko_tag.write( result + "\n");
//						writer_ko.write(sentence);
//						System.out.println(result);
//					}
//
//				}
//				reader.close();
//				writer_ko.close();
//
//			} catch (Exception e) {
//
//				e.printStackTrace();
//		 	}
//	}
//	@Test
//	public void appendToTrainData() {
//
//		try {
//			// APPEND MODE SET HERE
//			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
//					new File("/home/classfier/sewe-master/src/test/resources/data/data_total.txt"))));
//			int i = 0;
//			while (true) {
//				String line = reader.readLine();
//				if(StringUtils.isEmpty(line)) continue;	
//				Files.write(Paths.get("/home/classfier/sewe-master/src/test/resources/data/data_ko_main.txt"), (line + "\n").getBytes(),
//						StandardOpenOption.APPEND);
//			}
//
//		} catch (IOException ioe) {
//			ioe.printStackTrace();
//		}
//	} // end test()
//wc -l data_ko_main.txt  	
	//4066271 data_ko_main.txt
//	./fasttext sent2vec -input data_ko_main.txt -output wiki_model_new.bin -minCount 8 -dim 1000 -epoch 9 -lr 0.4 -wordNgrams 2 -loss ns -neg 10 -thread 20 -t 0.000005 -dropoutK 4 -minCountLabel 20 -bucket 4000000

	// @Test
	// public void test11TrainSen2VecCmd() {
	// System.out.printf("\nTraining sen2vec model ...\n");
	// SeWePredict jft = new SeWePredict();
	// jft.srunCmd(new String[] {
	// "sent2vec",
	// "-input", "/home/classfier/sent2vec-master/data_ko_main.txt",
	// "-output", "/home/classfier/sent2vec-master/wiki_model_new",
	// "-minCount", "8",
	// "-dim", "700",
	// "-epoch", "9",
	// "-lr", "0.4",
	// "-wordNgrams", "2",
	// "-loss", "ns",
	// "-neg", "10",
	// "-thread", "20",
	// "-t", "0.000005",
	// "-dropoutK", "4",
	// "-minCountLabel", "20",
	// "-bucket", "4000000"
	// });
	
}
/* failed 
 *  ./fasttext sent2vec -input data_ko_main_postag.txt -output wiki_model_new.bin -minCount 50 -dim 300 -epoch 20 -lr 0.4 -wordNgrams 3 -loss ns -neg 10 -thread 20 -t 0.000005 -dropoutK 4 -minCountLabel 20 -bucket 2000000
 *  
 *  ./fasttext sent2vec -input data_ko_main_postag.txt -output wiki_model_new.bin -minCount 20 -dim 700 -epoch 20 -lr 0.4 -wordNgrams 2 -loss ns -neg 10 -thread 20 -t 0.000005 -dropoutK 4 -minCountLabel 20 -bucket 4000000
./fasttext sent2vec -input data_ko_main_twitter.txt -output wiki_model_new.bin -minCount 50 -dim 900 -epoch 30 -lr 0.4 -wordNgrams 2 -loss ns -neg 10 -thread 20 -t 0.000005 -dropoutK 4 -minCountLabel 20 -bucket 2000000
./fasttext sent2vec -input data_ko_main_twitter.txt -output wiki_model_new.bin -minCount 100 -dim 1000 -epoch 30 -lr 0.4 -wordNgrams 2 -loss ns -neg 10 -thread 20 -t 0.000005 -dropoutK 4 -minCountLabel 20 -bucket 2000000
 53./fasttext sent2vec -input data_ko_main_twitter.txt -output wiki_model_new.tw -minCount 50 -dim 900 -epoch 40 -lr 0.6 -wordNgrams 2 -loss ns -neg 10 -thread 20 -t 0.000005 -dropoutK 4 -minCountLabel 20 -bucket 2000000
 55 ./fasttext sent2vec -input data_ko_main.txt -output wiki_model_new.bin -minCount 50 -dim 800 -epoch 20 -lr 0.4 -wordNgrams 2 -loss ns -neg 10 -thread 20 -t 0.000005 -dropoutK 4 -minCountLabel 20 -bucket 2000000
57.2./fasttext sent2vec -input data_ko_main_twitter.txt -output wiki_model_new.tw -minCount 30 -dim 900 -epoch 30 -lr 0.4 -wordNgrams 2 -loss ns -neg 10 -thread 20 -t 0.000005 -dropoutK 4 -minCountLabel 20 -bucket 2000000
./fasttext sent2vec -input namu_wiki_ko_tw.txt -output wiki_model_new.tw -minCount 30 -dim 900 -epoch 30 -lr 0.4 -wordNgrams 2 -loss ns -neg 10 -thread 20 -t 0.000005 -dropoutK 4 -minCountLabel 20 -bucket 2000000
 78 ./fasttext sent2vec -input namu_wiki_ko_tw.txt -output wiki_model_new.tw -minCount 100 -dim 300 -epoch 20 -lr 0.1 -wordNgrams 2 -loss ns -neg 10 -thread 20 -t 0.000005 -dropoutK 4 -minCountLabel 20 -bucket 2000000

 */
