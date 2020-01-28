# 문장임베딩

문장 임베딩(Sentence Embedding 혹은 Phrase나 Paragraph Embedding이라고도 함)은 문장 전체를 벡터 공간 상의 점으로 표현하는 기법을 말한다. 특히 벡터 공간 상에 점으로 문장을 옮겼을때, 의미적으로 유사한 문장끼리는 유사한 지점에 모이게 되는데, 이를 통해 분류나 클러스터링과 같은 기법 뿐만 아니라, 자동 질의 응답과 같은 더 복잡한 작업도 수행할 수 있다.
여기에는 Sent2Vec 모델을 통해 문장의 유사도를 판별하기 위해 비지도 학습 으로 대용량 코퍼스에 대해 별도의 레이블링 없이 학습 모델을 구축할 수 있는 코드를 포함하고 있다.


Table of Contents
=================

  * [Introduction](#introduction)
  * [Maven Dependency](#maven-dependency)
  * [Building](#building)
  * [Sentence Embeddings](#Sentence Embeddings)
  * [Downloading Sent2vec Pre-Trained Models](#Downloading Sent2vec Pre-Trained Models)
  * [Tokenizing](#Tokenizing)
  * [License](#license)
  * [References](#references)

## Introduction
sewe-vector is a Java wrapper for sent2vec's [fastText](https://github.com/epfml/sent2vec.git), 
a library for efficient learning of word embeddings and fast sentence classification. The JNI interface
is built using [javacpp](https://github.com/bytedeco/javacpp).

The library provides full fastText's command line interface. It also provides the API for
loading trained model from file to do label prediction in memory. Model training and quantization
are supported via the command line interface.

sewe-vector is ideal for building sent2vec classifiers in Java.

## Maven Dependency
Maven을 이용할 경우 pom.xml에 다음의 내용을 추가하면 됨:

```xml
  <dependency>
    <groupId>com.megazone.dx</groupId>
    <artifactId>swpredict</artifactId>
    <version>1.0</version>
  </dependency>
 ```

## Sentence Embeddings

Sent2Vec 구현에서는 각각의 n-그램에 대한 단어 ID 조합의해시에 버킷 사이즈를 모듈라 연산 한 결과를 키 값으로 한 새로운 단어 벡터를 생성한다.Sent2Vec은 fastText를 fork하여 구현까지 함께 공개했기 때문에  주목을 받고 있다. 
Sent2Vec은 CBOW의 확장형이다.Sent2Vec의 구현은 C++로 개발된 fastText 라이브러리에 알고리즘을 추가하고 개선하는 형태로 적용하였으며, 자바JNI인터페이스와 연동한다.


This will output sentence vectors (the features for each input sentence) to the standard output, one vector per line.
This can also be used with pipes:

```
cat text.txt | ./fasttext print-sentence-vectors model.bin
```

## Downloading Sent2vec Pre-Trained Models

- [sent2vec_wiki_unigrams](https://drive.google.com/open?id=0B6VhzidiLvjSa19uYWlLUEkzX3c) 5GB (600dim, trained on english wikipedia)
- [sent2vec_wiki_bigrams](https://drive.google.com/open?id=0B6VhzidiLvjSaER5YkJUdWdPWU0) 16GB (700dim, trained on english wikipedia)
- [sent2vec_twitter_unigrams](https://drive.google.com/open?id=0B6VhzidiLvjSaVFLM0xJNk9DTzg) 13GB (700dim, trained on english tweets)
- [sent2vec_twitter_bigrams](https://drive.google.com/open?id=0B6VhzidiLvjSeHI4cmdQdXpTRHc) 23GB (700dim, trained on english tweets)
- [sent2vec_toronto books_unigrams](https://drive.google.com/open?id=0B6VhzidiLvjSOWdGM0tOX1lUNEk) 2GB (700dim, trained on the [BookCorpus dataset](http://yknzhu.wixsite.com/mbweb))
- [sent2vec_toronto books_bigrams](https://drive.google.com/open?id=0B6VhzidiLvjSdENLSEhrdWprQ0k) 7GB (700dim, trained on the [BookCorpus dataset](http://yknzhu.wixsite.com/mbweb))

(as used in the NAACL2018 paper)

Note: users who downloaded models prior to [this release](https://github.com/epfml/sent2vec/releases/tag/v1) will encounter compatibility issues when trying to use the old models with the latest commit. Those users can still use the code in the release to keep using old models. 

## Tokenizing
twitter-korean-text는 normalization, tokenization, stemming, phrase extraction 이렇게 네가지 기능을 지원합니다.
스칼라로 쓰여진 한국어 처리기입니다. 현재 텍스트 정규화와 형태소 분석, 스테밍을 지원하고 있습니다. 짧은 트윗은 물론이고 긴 글도 처리할 수 있습니다.
정규화 normalization (입니닼ㅋㅋ -> 입니다 ㅋㅋ, 샤릉해 -> 사랑해)

한국어를 처리하는 예시입니닼ㅋㅋㅋㅋㅋ -> 한국어를 처리하는 예시입니다 ㅋㅋ
토큰화 tokenization

한국어를 처리하는 예시입니다 ㅋㅋ -> 한국어Noun, 를Josa, 처리Noun, 하는Verb, 예시Noun, 입Adjective, 니다Eomi ㅋㅋKoreanParticle
어근화 stemming (입니다 -> 이다)

한국어를 처리하는 예시입니다 ㅋㅋ -> 한국어Noun, 를Josa, 처리Noun, 하다Verb, 예시Noun, 이다Adjective, ㅋㅋKoreanParticle
어구 추출 phrase extraction

한국어를 처리하는 예시입니다 ㅋㅋ -> 한국어, 처리, 예시, 처리하는 예시

## Train a New Sent2vec Model

To train a new sent2vec model, you first need some large training text file. This file should contain one sentence per line. The provided code does not perform tokenization and lowercasing, you have to preprocess your input data yourself, see above.
학습 데이터는 2015년 부터 2019년 까지의 모든 언론사의 뉴스를 형태소 분석한 결과로 4.5억개의 문장, 120억개의 단어,85G 크기의 파일로 구성되어 있다. Sent2Vec은 비지도 학습 모델이므로 문장에 대한 별도의 레이블링은 진행하지 않는다.

You can then train a new model. Here is one example of command:

    ./fasttext sent2vec -input namu_wiki_ko_tw.txt -output wiki_model_new.tw -minCount 30 -dim 900 -epoch 30 -lr 0.4 -wordNgrams 2 -loss ns -neg 10 -thread 20 -t 0.000005 -dropoutK 4 -minCountLabel 20 -bucket 2000000
    

Here is a description of all available arguments:

```
sent2vec -input train.txt -output model

The following arguments are mandatory:
  -input              training file path
  -output             output file path

The following arguments are optional:
  -lr                 learning rate [0.2]
  -lrUpdateRate       change the rate of updates for the learning rate [100]
  -dim                dimension of word and sentence vectors [100]
  -epoch              number of epochs [5]
  -minCount           minimal number of word occurences [5]
  -minCountLabel      minimal number of label occurences [0]
  -neg                number of negatives sampled [10]
  -wordNgrams         max length of word ngram [2]
  -loss               loss function {ns, hs, softmax} [ns]
  -bucket             number of hash buckets for vocabulary [2000000]
  -thread             number of threads [2]
  -t                  sampling threshold [0.0001]
  -dropoutK           number of ngrams dropped when training a sent2vec model [2]
  -verbose            verbosity level [2]
  -maxVocabSize       vocabulary exceeding this size will be truncated [None]
  -numCheckPoints     number of intermediary checkpoints to save when training [1]
```

## Nearest Neighbour Search and Analogies
Given a pre-trained model `model.bin` , here is how to use these features. For the nearest neighbouring sentence feature, you need the model as well as a corpora in which you can search for the nearest neighbouring sentence to your input sentence. We use cosine distance as our distance metric. To do so, we use the command `nnSent` and the input should be 1 sentence per line:

```
./fasttext nnSent model.bin corpora [k] 
```
k is optional and is the number of nearest sentences that you want to output.     

For the analogiesSent, the user inputs 3 sentences A,B and C and finds a sentence from the corpora which is the closest to D in the A:B::C:D analogy pattern.
```
./fasttext analogiesSent model.bin corpora [k]
```

k is optional and is the number of nearest sentences that you want to output.     

# Unigram Embeddings 

For the purpose of generating word representations, we compared word embeddings obtained training sent2vec models with other word embedding models, including a novel method we refer to as CBOW char + word ngrams (`cbow-c+w-ngrams`). This method augments fasttext char augmented CBOW with word n-grams. You can see the full comparison of results in [*this paper*](https://www.aclweb.org/anthology/N19-1098). 


# References
When using this code or some of our pre-trained models for your application, please cite the following paper for sentence embeddings:

  Matteo Pagliardini, Prakhar Gupta, Martin Jaggi, [*Unsupervised Learning of Sentence Embeddings using Compositional n-Gram Features*](https://aclweb.org/anthology/N18-1049) NAACL 2018

```
@inproceedings{pgj2017unsup,
  title = {{Unsupervised Learning of Sentence Embeddings using Compositional n-Gram Features}},
  author = {Pagliardini, Matteo and Gupta, Prakhar and Jaggi, Martin},
  booktitle={NAACL 2018 - Conference of the North American Chapter of the Association for Computational Linguistics},
  year={2018}
}
```

For word embeddings:

Prakhar Gupta, Matteo Pagliardini, Martin Jaggi, [*Better Word Embeddings by Disentangling Contextual n-Gram
Information*](https://www.aclweb.org/anthology/N19-1098) NAACL 2019

```
@inproceedings{DBLP:conf/naacl/GuptaPJ19,
  author    = {Prakhar Gupta and
               Matteo Pagliardini and
               Martin Jaggi},
  title     = {Better Word Embeddings by Disentangling Contextual n-Gram Information},
  booktitle = {{NAACL-HLT} {(1)}},
  pages     = {933--939},
  publisher = {Association for Computational Linguistics},
  year      = {2019}
}
```