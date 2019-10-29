# BigData miniProject

**BigData Pipeline**

- [Data 수집] ▶ [Data 직렬화] ▶ [Data 저장] ▶ [Data 처리] ▶ [Data 분석] ▶ [Data 시각화] ▶ [Data 관리]

[BigData Pipeline]
![pipeline](images/pipeline.jpg)

## [Data 수집]
- Logstash, Beats (Packet/Top/File beats)
- **Apache Flume**, Yahoo Chuckwa, Facebook Scribe, Apache Sqoop

### ☞ Apache Kafka [![kafka](https://img.shields.io/badge/Apache-Kafka-blue)](https://kafka.apache.org/documentation/)&nbsp;[![quickstart](https://img.shields.io/badge/Kafka-Quickstart-red)](https://kafka.apache.org/quickstart)&nbsp;

[Kafka Architecture] [![Sources](https://img.shields.io/badge/출처-Kafka-yellow)](https://kafka.apache.org/intro)

![kafka](images/apache_kafka.png)

```js
- Producer API : 한 개 이상의 topic에 stream을 publish할 수 있는 응용 프로그램을 제작
- Consumer API : 한 개 이상의 topic을 subscribe하고 생성된 record stream을 처리
- Streams  API : 한 개 이상의 topic에서 메시지의 직렬화/역직렬화를 처리하고 상태 저장 작업에 필요한 상태를 유지, Kafka Cluster와 통신하는 Java Client Library (현재 Scala)
- Connector API: kafka에 기록된 data를 기존 응용 프로그램 또는 DB에 연결하는 재사용 가능한 Producer 또는 Consumer를 구축 및 실행
```

- Apachae Camus : 카프카에서 hdfs로 데이터를 배치로 옮겨주는 역할

### ★Run (On Windows)

- Download : http://mirror.navercorp.com/apache/kafka/2.3.1/

- Run Zookeeper/Kafka & Create Topic & Run Consumer/Producer

```java
# Zookeeper Run
> bin/windows/zookeeper-server-start.bat config/zookeeper.properties

# Kafka Run
> bin/windows/kafka-server-start.bat config/server.properties

# Topic 생성
> bin/windows/kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic kafka-test.t

# Topic List 확인
> bin/windows/kafka-topics.bat --list --zookeeper localhost:2181

# Topic 삭제 : /temp/zookeeper & kafka-logs로 관리
> bin/windows/kafka-topics.bat --delete --zookeeper localhost --topic kafka-test.t

# Consumer Run -> Get Message
> bin/windows/kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic kafka-test.t

# Producer Run -> Send Message
> bin/windows/kafka-console-producer.bat --broker-list localhost:9092 --topic kafka-test.t
> test message#1
> test message#2
> test message#3
```

- Run API : http://localhost:8080/get?message=kafka-test-message

```java
# Run Zookeeper & Kafka
# Create Topic
# JUnit Test KafkaExampleApplicationTests
```

#### <설계/고려사항>
- Kafka는 실제로 대규모 Stream을 처리하도록 설계되어 규모가 없거나 예상하지 못한 경우, 설정하고 유지/관리하는 것은 가치가 없음

```js
- 장애 발생 시 데이터 처리 보장
- 누락 데이터 확인을 위한 메타정보 추가
- 데이터 복구 방안
```

---

## [Data 직렬화]

#### Apache Parquet [![parquet](https://img.shields.io/badge/Apache-Parquet-orange)](https://parquet.apache.org/)&nbsp;

- Apache Parquet는 데이터 처리 프레임 워크, 데이터 모델 또는 프로그래밍 언어의 선택에 관계없이 Hadoop 에코 시스템의 모든 프로젝트에서 사용할 수 있는 컬럼 스토리지 형식

```js
- Hadoop HDFS에서 주로 사용하는 파일 포맷
- 중첩된 데이터를 효율적으로 저장할 수 있는 컬럼 기준 저장 포맷 <- 압축률이 더 좋다.
- 컬럼 기준 포맷은 파일 크기와 쿼리 성능 측면에 모두 효율성이 높음 <- I/O 사용률이 줄어든다.
- 자바 구현체는 단일 표현에 얽매이지 않기 때문에 Parquet 파일에서 데이터를 읽고 쓰는데 Avro, Thrift, Protocol Buffer의 In-memory 데이터 모델을 사용할 수 있음
```

#### Apache Thrift [![thrift](https://img.shields.io/badge/Apache-Thrift-orange)](https://thrift.apache.org/)&nbsp;

- 확장 가능한 언어 간 서비스 개발을 위한 Apache Thrift 소프트웨어 프레임 워크는 소프트웨어 스택과 코드 생성 엔진을 결합 (C ++, Java, Python, PHP, Ruby, Erlang, Perl, Haskell, C #, Cocoa, JavaScript, Node.js, Smalltalk, OCaml 및 Delphi 및 기타 언어)

```js
- Facebook에서 개발한 서로 다른 언어로 개발된 모듈들의 통합을 지원하는 RPC Framework
```

### ☞ Google Protocol Buffer [![gpb](https://img.shields.io/badge/Google-Protocol_Buffer-orange)](https://developers.google.com/protocol-buffers)&nbsp;

- 프로토콜 버퍼는 구조화된 데이터를 직렬화하기 위한 Google의 언어, 플랫폼 중립적이며 확장 가능한 메커니즘으로 XML은 작지만 더 빠르고 단순함

```js
- 구글에서 개발 오픈한 Serialized Data Structure
- C++,C#, Go, Java, Python, Object C, Javascript, Ruby 등 다양한 언어를 지원하며, 직렬화 속도가 빠르고 직렬화된 파일의 크기도 작아서 Apache Avro 파일 포맷과 함께 많이 사용됨
- GRPC Network Protocol의 경우 HTTP 2.0 을 기반으로 하여 직렬화
- 하나의 파일에 최대 64M 까지 지원 가능하며, JSON 파일을 프로토콜 버퍼 파일 포맷으로 전환이 가능하고, 반대로 프로토콜 버퍼 파일도 JSON으로 전환이 가능
```

### ☞ Apache Avro [![avro](https://img.shields.io/badge/Apache-Avro-orange)](http://avro.apache.org/docs/current/)&nbsp;

- Avro는 언어 독립적인 직렬화 라이브러리로 핵심 구성 요소 중 하나인 스키마를 사용. 추가 데이터 처리를 위해 스키마를 JSON 파일에 저장함

```js
- 특정 언어에 종속되지 않은 언어 중립적 데이터 직렬화 시스템
- 하둡 Writable(직렬화 방식)의 주요 단점인 언어 이식성(language portablility)을 해결하기 위해 만든 프로젝트
- 스키마는 JSON으로 작성
- 데이터는 작고 빠른 바이너리 포맷으로 직렬화
```

### ★Run

- Apache Avro를 사용하여 AvroHttRequest Class를 직렬화 및 역직렬화 수행 example code

+ maven 의존성 추가

```xml
<dependency>
    <groupId>org.apache.avro</groupId>
    <artifactId>avro-compiler</artifactId>
    <version>1.8.2</version>
</dependency>
<dependency>
    <groupId>org.apache.avro</groupId>
    <artifactId>avro-maven-plugin</artifactId>
    <version>1.8.2</version>
</dependency>
```

+ 스키마 생성 : SchemaBuilder를 사용

```java
Schema clientIdentifier = SchemaBuilder.record("ClientIdentifier")
  .namespace("com.baeldung.avro")
  .fields().requiredString("hostName").requiredString("ipAddress")
  .endRecord();
```

+ 프로그램에서 스키마 읽기 : 주어진 스키마에 대한 Avro 클래스를 만드는 것
	- 프로그래밍 방식으로 Avro 클래스 생성 : SchemaCompiler를 사용하여 클래스를 생성하는 방법
	- Maven을 사용하여 클래스 생성하는 방법 <- 이 방법으로 Class 생성

```xml
<plugin>
    <groupId>org.apache.avro</groupId>
    <artifactId>avro-maven-plugin</artifactId>
    <version>${avro.version}</version>
        <executions>
            <execution>
                <id>schemas</id>
                <phase>generate-sources</phase>
                <goals>
                    <goal>schema</goal>
                    <goal>protocol</goal>
                    <goal>idl-protocol</goal>
                </goals>
                <configuration>
                    <sourceDirectory>${project.basedir}/src/main/resources/</sourceDirectory>
                    <outputDirectory>${project.basedir}/src/main/java/</outputDirectory>
                </configuration>
            </execution>
        </executions>
</plugin>
```

+ Avro를 사용 데이터 직렬화
+ 데이터 역 직렬화

---

## [Data 저장]

- Apache Hadoop HDFS (NameNode + DataNode)
- Apache HBASE (HDFS 컬럼 기반 DB), Apache Kudu (컬럼 지향 데이터 스토어)

### ☞ ElasticSearch Cluster
- 전체 데이터를 저장하고 모든 노드를 포괄하는 통합 색인화 및 NRT(Near Realtime) 검색 기능을 제공

```js
- Cluster + Master-eligible/Data/Ingest/Tribe Node로 구성
- 대용량 데이터 모두 저장을 위해서는 Storage 뿐만 아니라, Index를 유지를 위해 많은 메모리가 요구되어 좋은 성능의 Node들이 필요 -> 많은 비용 발생
- 데이터 유실 발생 가능 -> 많은 운용 능력 요구됨
- 비교적 적은 양의 데이터를 처리하는데 알맞음

  1. 컬럼 지향 ( column - stride )
  2. 분산 데이터 스토어
  3. 비공유 모델 ( shared-nothing )
  4. 검색에 용이한 인덱스 포맷. 역색인 구조 ( 검색 엔진에서 사용하고 있는 )
```

### ※ 분산/컬럼 지향 DB에서 TSDB 및 OLAP DB로 전환 및 통합 추세

### ☞ Time Series Database (TSD)

#### InfluxDB [![influxdb](https://img.shields.io/badge/TSD-InfluxDB-brightgreen)](https://www.influxdata.com/)&nbsp;

```js
- 2013 년 InfluxData에 의해 구축, 대부분 Grafana와 함께 사용
- No Schema, NoSQL 특징을 기반으로 하며 빠른 데이터베이스 스키마 수정이 가능
- InfluxDB와 직접 바인딩하는 데이터 처리 엔진 및 많은 수의 실시간 Metric을 수집할 수 있는 50 개 이상의 Agent Set를 제공
```

### ☞ OpenTSDB [![opentsdb](https://img.shields.io/badge/TSD-OpenTSDB-brightgreen)](https://www.opentsdb.net)&nbsp;

- OpenTSDB는 Hbase 위에서 작성된 분산, 확장 가능한 TSD(Time Series Database)

```js
- 수집 대상인 server 또는 network 장비들에 설치된 collector 클라이언트가 TSD서버로 전송하면 TSD가 HBase에 저장
- OpenTSDB는 HTTP API, Web UI, Telnet을 통한 읽기/쓰기를 지원
- openTSDB는 HBase 기반으로 작동을 하기 때문에 HBase가 이미 설치 되어 있어야 함
```

[Data Format]

- Metric name
- Unix timestamp(Epoch)
- a Value(int64, float, JSON)
- A set of tags

[OpenTSDB Architecture] [![Sources](https://img.shields.io/badge/출처-OpenTSDB-yellow)](http://opentsdb.net/overview.html)

![OpenTSDB](images/opentsdb_architecture.png)

```js
- 시계열 데이터를 매우 큰 규모로 저장해야 하는 필요성을 해결
- Apache HBase에 구축 된 스키마 없는 Column 기반인 NoSQL의 데이터베이스
- 분산 TSD 서버 인스턴스에 수억 개의 데이터 행 저장 가능
```

### ☞ Apache Druid (incubating project) [![druid](https://img.shields.io/badge/Apache-Druid-blue)](https://druid.apache.org/)&nbsp;
- A Scalable Timeseries Online Analytical Processing(OLAP) Database System

[Druid Architecture] [![Sources](https://img.shields.io/badge/출처-Druid-yellow)](https://druid.apache.org/)

![druid](images/druid_architecture.png)

```js
- Real-time/Historical/Broker/Coordinator/Deep/MySQL/Zookeeper Node로 구성

- 대용량 데이터에 대한 실시간 집계 (Real Time Aggregations) <- 대용량 Spark Cluster 필요
- 페타 바이트 크기의 데이터 세트에 대한 빠른 집계 쿼리를 위한 데이터웨어 하우징 솔루션
- Druid는 대기 시간이 매우 짧은 쿼리에 중점을 두고 있으며, 수천 명의 사용자가 사용하는 응용 프로그램에 적합
- Real-time, Multi-Tenancy, 컬럼 지향, 쿼리 속도 보장을 위해 만들어 짐
- Lambda-Architecture : 실시간으로 들어오는 데이터(실시간 뷰)와 이전 데이터(배치 뷰)를 합해 쿼리 결과를 보여준다
- Druid는 모든 데이터를 완전히 색인화 함 (Full Indexing)
```

### ☞ Amazon S3, Azure ADLS & WASB, Google Cloud GCS
- Cloud Connector를 사용하여 Cloud Storage에 저장된 데이터에 액세스하고 작업 가능

[Cloud Storage Architecture]

![cloud_storage](images/cloud_storage.png)

```js
- 분석할 데이터를 수집 후, Cloud Storage 서비스에서 직접 Hive 또는 Spark와 같은 Hadoop Echo System Application에 Load
- 클러스터 외부에서 사용할 수 있도록 데이터를 클라우드 스토리지 서비스에 유지
- 클라우드 스토리지 서비스에 저장된 데이터를 분석을 위해 HDFS로 복사한 다음 완료되면 클라우드로 다시 복사
```

#### <설계/고려사항>
```js
- 데이터 사용에 대한 고가용성 보장
- 압축 파일 포맷에 대한 고민
- 장애에 대한 영향도 전파가 없도록 구성
- Shards, Replica 전략
```

---

## [Data 처리]

#### Hadoop MapReduce (분산데이터 병렬배치 처리)

```js
- Hadoop은 데이터 일괄처리를 최우선으로 하며, 페타바이트급의 데이터를 저렴한 비용으로 저장/처리할 수 있으나, 실시간 데이터 처리에 부족
```

**[Data Warehouse]**

- Hive : Hadoop에서 동작하는 data warehouse infra architecture, SQL을 MapReduce로 변환
- Tajo : Hadoop 기반의 대용량 data warehouse

### ☞ Apache Spark™ [![spark](https://img.shields.io/badge/Apache-Spark-yellow)](https://spark.apache.org/)&nbsp;
- 대규모 데이터 처리 및 라이브러리 세트 (Spark SQL/MLlib/GraphX)를 위한 통합 분석 엔진

[Apache Spark]

![apache_spark](images/apache_spark.png)

[Apache Spark Architecture]

![apache_spark](images/spark_architecture.png)

```js
- In-Memory 방식 오픈 소스 클러스터 컴퓨팅 프레임워크, 실시간 데이터 처리에 적합
- 메모리를 활용한 아주 빠른 데이터 처리, Scala를 사용하여 코드가 매우 간단, interactive shell을 사용
- Spark는 실시간 처리를 위한 독립적인 처리엔진으로 Hadoop과 같은 모든 분산 파일 시스템에 설치 가능
- Spark는 스트리밍 데이터로의 전환을 편리하게 할 수 있다는 장점
```

#### <설계/고려사항>

```js
- 데이터 처리 후 정합성 체크를 위한 원본 데이터 보관
```

---

## [Data 분석]

#### Apache Impala [![impala](https://img.shields.io/badge/Apache-Impala-yellowgreen)](https://impala.apache.org)&nbsp;
- Apache Impala는 Apache Hadoop을 실행하는 컴퓨터 클러스터에 저장된 데이터를위한 오픈 소스 MPP (대규모 병렬 처리) SQL 쿼리 엔진 

#### Presto [![presto](https://img.shields.io/badge/Apache-Presto-yellowgreen)](http://prestodb.github.io)&nbsp;
- Presto는 기가 바이트에서 페타 바이트에 이르는 모든 크기의 데이터 소스에 대해 대화 형 분석 쿼리를 실행하기위한 오픈 소스 분산 SQL 쿼리 엔진

### ☞ Apache Lucene [![lucene](https://img.shields.io/badge/Apache-Lucene-yellowgreen)](https://lucene.apache.org)&nbsp;
- Apache Lucene은 무료 오픈 소스 검색 엔진 소프트웨어 라이브러리로 Doug Cutting이 Java로 작성

[Apache Lucene Architecture]

![apache_lucene](images/lucene_architecture.png)

```js
- 색인과 검색 기능 제공, 자바 기반 검색 Library
- Lucene Core : 맞춤법 검사, 적중 강조 표시 및 고급 분석/토큰화 기능뿐만 아니라 Java 기반 색인 및 검색 기술을 제공
- Solr는 XML/HTTP 및 JSON/Python/Ruby API와 함께 Lucene Core를 사용하여 구축된 고성능 검색 서버로, hit highlighting, faceted search, caching, 복제 및 웹 관리 Interface
- PyLucene은 Core Project를 중심으로 한 Python Wrapper
```

### ☞ Elasticsearch [![elasticsearch](https://img.shields.io/badge/Elasticsearch-v7.4-yellowgreen)](https://www.elastic.co/kr/)&nbsp;

[Elasticsearch Context View]

![elasticsearch](images/elasticsearch.png)

```js
- Lucene기반, 사이즈가 작은 데이터에 대한 속성검색/연관검색/실시간 검색에 용이함 (주요 커머스검색 용)
- 자체 Master Node에서 관리, 강력한 API (RESTful 검색 및 분석 엔진)
```

**Apache Solr(솔라)**

```js
- Lucene기반, 사이즈가 큰 데이터 검색에 용이에 문서 검색에 적합하나 색인주기가 느림 (주로 문서검색 용)
- Apache ZooKeeper로 관리
```

**Scruid (Scala+Druid)**

```js
- Scala에서 Druid Query를 쉽게 작성할 수있는 Open Source Library
- Library는 Query를 JSON으로 변환하고 사용자가 정의한 Case Class의 결과를 구문 분석
```

---

## [Data 시각화]

#### Apache Zeppelin [![zepplin](https://img.shields.io/badge/Apache-Zeppelin-ff69b4)](https://zeppelin.apache.org)&nbsp;

```js
- 국내에서 주도하고 있는 오픈소스 프로젝트로써, Spark를 훨씬 더 편하고 강력하게 사용할 수 있게 해주는 도구
- 분석 코드 작성, 작업 스케쥴링, 데이터 시각화, 대시보드
- 여러 시스템에 대한 실행 결과를 얻기 위한 Interpreter들을 통해 Query 결과를 수집/시각화
```

### ☞ Kibana [![kibana](https://img.shields.io/badge/ELK-Kibana-ff69b4)](https://www.elastic.co/kr/products/kibana)&nbsp;
- Kibana는 로그 및 시계열 분석, 응용 프로그램 모니터링 및 운영 인텔리전스 사용 사례에 사용되는 오픈 소스 데이터 시각화 및 탐색 도구

```js
- 로그 데이터 탐색에 사용되는 ELK Stack의 일부
- Elasticsearch 클러스터에 저장된 로그 데이터를 기반으로 대시 보드를 탐색, 시각화 및 구축 할 수있는 도구
- 주로 로그 메시지를 분석하는 데 사용
- YAML 구성 파일
- Elasticsearch에서만 작동하도록 설계되어 다른 유형의 데이터 소스를 지원 안함
- 즉시 사용 가능한 경고 기능이 제공되지 않으며, noti를 추가하려면 Logz.io와 같은 호스팅된 ELK 스택을 선택하거나 ElastAlert를 구현하거나 X-Pack을 사용해야 함
```

### ☞ Grafana [![grafana](https://img.shields.io/badge/OpenSource-Grafana-ff69b4)](https://grafana.com)&nbsp;
- Grafana는 모든 데이터베이스를위한 오픈 소스 분석 및 모니터링 솔루션

```js
- Graphite 또는 InfluxDB와 같은 시계열 데이터베이스와 함께 메트릭 분석에 사용되는 조합
- Graphite, InfluxDB 및 Elasticsearch 및 Logz.io와 함께 가장 많이 사용되는 오픈 소스 시각화 도구
- 시스템 CPU, 메모리, 디스크 및 I / O 사용률과 같은 메트릭을 분석하고 시각화하도록 설계
- 전체 텍스트 데이터 쿼리를 허용하지 않음
- .ini 구성 파일
- 여러 시계열 데이터 저장소에서 작동 가능
- 내장 된 사용자 제어 및 인증 메커니즘을 제공하여 외부 SQL 또는 LDAP 서버 사용을 포함하여 대시 보드에 대한 액세스를 제한하고 제어 가능
- 사용 된 구문이 데이터 소스에 따라 다름
- 버전 4.x부터 사용자가 선택한 알림 엔드 포인트(예: 이메일,Slack,PagerDuty,사용자정의 웹 후크)에 대해 경고를 트리거하는 조건부 규칙을 대시 보드 패널에 첨부 가능한 내장 경고 엔진을 제공
```

[Kibana vs. Grafana]

![google_trend](images/kabana_vs_grafana.png)

---

## [Data 관리]

#### Apache Oozie [![oozie](https://img.shields.io/badge/Apache-Oozie-blueviolet)](https://oozie.apache.org)&nbsp;
- Hadoop의 Job을 관리하기 위한 서버 기반의 Workflow Scheduler System

#### Azkaban [![azkaban](https://img.shields.io/badge/LinkedIn-Azkaban-blueviolet)](https://azkaban.github.io)&nbsp;
- Azkaban은 Hadoop job을 실행하기 위해 LinkedIn에서 만든 Batch Workflow Job Scheduler

#### Apache Ambari [![ambari](https://img.shields.io/badge/Apache-Ambari-blueviolet)](https://ambari.apache.org)&nbsp;
- 시스템 관리자가 Hadoop 클러스터를 프로비저닝, 관리 및 모니터링하고 Hadoop을 기존 엔터프라이즈 인프라와 통합 할 수 있게 해 줌

[Ambari Screenshot]

![ambari](images/ambari.png)

```
- RESTful API가 지원하는 직관적이고 사용하기 쉬운 Hadoop 관리 웹 UI를 제공
- 여러 호스트에 Hadoop 서비스를 설치하기 위한 단계별 마법사를 제공
- 클러스터에 대한 Hadoop 서비스 구성을 처리
- 전체 클러스터에서 Hadoop 서비스를 시작, 중지 및 재구성하기위한 중앙 관리를 제공
- Hadoop 클러스터의 상태 및 상태를 모니터링하기위한 대시 보드를 제공
```

### ☞ Apache Airflow [![airflow](https://img.shields.io/badge/Apache-Airflow-blueviolet)](https://airflow.apache.org)&nbsp;
- Airflow는 프로그래밍 방식으로 Workflow를 작성, 예약 및 모니터링 할 수 있는 Platform

[Airflow Screenshot]

![airflow](images/airflow.png)

```
- Apache Airflow는 배치 스케쥴링(파이프라인) 플랫폼
- Python 기반으로 실행할 Task(Operator)를 정의하고 순서에 등록 & 실행 & 모니터링
- DAG(Directed Acyclic Graph, 비순환 방향 그래프)로 각 배치 스케쥴 관리
```

#### Apache NiFi [![nifi](https://img.shields.io/badge/Apache-NiFi-blueviolet)](https://nifi.apache.org)&nbsp;
- Process와 Process간 Data Flow Monitoring Tool

``` 
- Dataflow를 쉽게 개발할 수 있고, 시스템 간의 데이터 이동과 내용을 볼 수 있는 기능과 UI를 제공, 데이터의 input/output을 볼 수 있음
```

---

## [Data 통합 서비스/솔루션]

### ☞ Amazon Elasticsearch Service [![aws](https://img.shields.io/badge/AWS-Elasticsearch-lightgrey)](https://aws.amazon.com/ko/elasticsearch-service)&nbsp;

```
- 손쉽게 Elasticsearch를 배포, 보호, 운영 및 확장이 가능한 가동 중단이 없는 완전 관리형 서비스
- 사용하는 만큼만 비용을 지불하며 사전 비용이나 사용 요건이 없음
```

### ☞ Splunk [![splunk](https://img.shields.io/badge/Solution-Splunk-lightgrey)](https://www.splunk.com/ko_kr)&nbsp;
- 로그 데이터, 실시간 이벤트 데이터 및 다양한 장비 데이터를 수집하고 모니터링하며 검색, 분류, 분석할 수 있는 엔진을 제공하는 통합 솔루션

[Splunk Architecture]

![splunk_architecture](images/splunk_architecture.png)
