package com.megazone.dx.ai;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import com.mysql.cj.core.util.StringUtils;
import com.twitter.penguin.korean.KoreanTokenJava;
import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer;
import com.twitter.penguin.korean.tokenizer.Sentence;

import scala.collection.Seq;
//@RunWith(SpringRunner.class)
//@SpringBootTest
/*
 * wget http://download.wikimedia.org/kowiki/latest/kowiki-latest-pages-articles.xml.bz2
 * WikiExtractor.py -cb 250K -o extracted kowiki-latest-pages-articles.xml.bz2
 * cat extracted//wiki_>final.xml
 * ./fasttext sent2vec -input data_ko_main.txt -output wiki_model -minCount 8 -dim 700 -epoch 9 -lr 0.2 -wordNgrams 2 -loss ns -neg 10 -thread 20 -t 0.000005 -dropoutK 4 -minCountLabel 20 -bucket 4000000
 * ./fasttext nnSent wiki_model.bin tag_irqa.txt
 */
/* in pom.xml
 *         <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        
        <dependency>
		    <groupId>com.twitter.penguin</groupId>
		    <artifactId>korean-text</artifactId>
		    <version>4.4</version>
		  </dependency>
		  <dependency>
 */
public class MakeWikiDataTest {
//	@Test
//	public void contextLoads() {
	public static void main(String[] args) {
		  System.out.println("/extract");
	        

			BufferedReader reader;
			PrintWriter writer_ko;
			try {
				writer_ko = new PrintWriter("/home/classfier/sewe-master/src/test/resources/data/data_ko_main.txt");
				File file = new File("/home/classfier/sewe-master/src/test/resources/data/final.xml");

				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));


				while (true) {
					final String line = reader.readLine();
					if (line == null)
						break;
					if(StringUtils.isNullOrEmpty(line)|| !line.contains(".") || line.startsWith("<doc")|| line.startsWith("</doc>")) continue;

					List<Sentence> splitedList = TwitterKoreanProcessorJava.splitSentences(line);
					String result = "";
					for(Sentence sSentence: splitedList) {
						String sentence = sSentence.text();
						if(StringUtils.isNullOrEmpty(sentence)) continue;
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
							result = result + token.getText()+ " ";//+"/"+ token.getPos().name()+" ";													
						if(StringUtils.isNullOrEmpty(result)) continue;
						writer_ko.write( result + "\n");						
						System.out.println(result);
					}

				}
				reader.close();
				writer_ko.close();

			} catch (IOException e) {

				e.printStackTrace();
		 	}
	}
	
	

}
