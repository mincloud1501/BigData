# BigData miniProject

**BigData Pipeline**

[BigData Pipeline]
![pipeline](images/pipeline.jpg)

## [Data 수집]
- Logstash, Beats (Packet/Top/File beats)
- **Apache Flume**, Yahoo Chuckwa, Facebook Scribe, Apache Sqoop

### ☞ Apache Kafka
```
- Distributed Queue Messaging System
```

- Apachae Camus : 카프카에서 hdfs로 데이터를 배치로 옮겨주는 역할


 **`※설계/고려사항`**
```
- 장애 발생 시 데이터 처리 보장
- 누락 데이터 확인을 위한 메타정보 추가
- 데이터 복구 방안
```

## [Data 직렬화]

#### 파케이(Parquet)

```
- Hadoop HDFS에서 주로 사용하는 파일 포맷
- 중첩된 데이터를 효율적으로 저장할 수 있는 컬럼 기준 저장 포맷
- 컬럼 기준 포맷은 파일 크기와 쿼리 성능 측면에 모두 효율성이 높음
- 자바 구현체는 단일 표현에 얽매이지 않기 때문에 파케이 파일에서 데이터를 읽고 쓰는데 에이브로, 쓰리프트, 프로토콜 버퍼의 인메모리 데이터 모델을 사용할 수 있음
```

### Apache Thrift

```
- 서로 다른 언어로 개발된 모듈들의 통합을 지원하는 RPC Framework
```

### ☞ 에이브로(Avro)
```
- 특정 언어에 종속되지 않은 언어 중립적 데이터 직렬화 시스템
- 하둡 Writable(직렬화 방식)의 주요 단점인 언어 이식성(language portablility)을 해결하기 위해 만든 프로젝트
- 스키마는 JSON으로 작성
- 데이터는 작고 빠른 바이너리 포맷으로 직렬화
```

---

## [Data 저장]

- Apache Hadoop HDFS (NameNode + DataNode)
- Apache HBASE (HDFS 컬럼 기반 DB), Apache Kudu (컬럼 지향 데이터 스토어)

### ☞ ElasticSearch Cluster
- 전체 데이터를 저장하고 모든 노드를 포괄하는 통합 색인화 및 NRT(Near Realtime) 검색 기능을 제공

```
- Cluster + Master-eligible/Data/Ingest/Tribe Node로 구성
- 대용량 데이터 모두 저장을 위해서는 Storage 뿐만 아니라, Index를 유지를 위해 많은 메모리가 요구되어 좋은 성능의 Node들이 필요 -> 많은 비용 발생
- 데이터 유실 발생 가능 -> 많은 운용 능력 요구됨
- 비교적 적은 양의 데이터를 처리하는데 알맞음

  1. 컬럼 지향 ( column - stride )
  2. 분산 데이터 스토어
  3. 비공유 모델 ( shared-nothing )
  4. 검색에 용이한 인덱스 포맷. 역색인 구조 ( 검색 엔진에서 사용하고 있는 )
```

### ※ 분산 컬럼 지향 DB에서 TSDB 및 OLAP DB로 전환 및 통합 추세###

[![druid](https://img.shields.io/badge/Apache-Druid-blue)](https://druid.apache.org/)&nbsp;

### ☞ Apache Druid (incubating project)
- A Scalable Timeseries Online Analytical Processing(OLAP) Database System

[Druid Architecture]

![druid](images/druid_architecture.png)

```
- Real-time/Historical/Broker/Coordinator/Deep/MySQL/Zookeeper Node로 구성

- 대용량 데이터에 대한 실시간 집계 (Real Time Aggregations) <- 대용량 Spark Cluster 필요
- 페타 바이트 크기의 데이터 세트에 대한 빠른 집계 쿼리를 위한 데이터웨어 하우징 솔루션
- Druid는 대기 시간이 매우 짧은 쿼리에 중점을 두고 있으며, 수천 명의 사용자가 사용하는 응용 프로그램에 적합
- Real-time, Multi-Tenancy, 컬럼 지향, 쿼리 속도 보장을 위해 만들어 짐
- Lambda-Architecture : 실시간으로 들어오는 데이터(실시간 뷰)와 이전 데이터(배치 뷰)를 합해 쿼리 결과를 보여준다
- Druid는 모든 데이터를 완전히 색인화 함 (Full Indexing)
```

### ☞ Amazon S3, Azure Blob Storage, Google Cloud Storage

**`※설계/고려사항`**
```
- 데이터 사용에 대한 고가용성 보장
- 압축 파일 포맷에 대한 고민
- 장애에 대한 영향도 전파가 없도록 구성
- Shards, Replica 전략
```

---

## [Data 처리]

- Hadoop MapReduce (분산 데이터 병렬 배치 처리)

```
- Hadoop은 데이터 일괄처리를 최선으로 하며, 페타바이트급의 데이터를 저렴한 비용으로 저장/처리할 수 있으나, 실시간 데이터 처리에 부족
```

**[Data Warehouse]**

- Hive : Hadoop에서 동작하는 data warehouse infra architecture, SQL을 MapReduce로 변환, 페이스북에서 개발 넷플릭스 등과 같은 회사에서 사용
- Tajo : Hadoop 기반의 대용량 data warehouse

### ☞ Apache Spark
```
- In-Memory 방식 오픈 소스 클러스터 컴퓨팅 프레임워크
- 메모리를 활용한 아주 빠른 데이터 처리, Scala를 사용하여 코드가 매우 간단, interactive shell을 사용
- SPARK는 실시간 처리를 위한 독립적인 처리엔진으로 Hadoop과 같은 모든 분산 파일 시스템에 설치 가능
- Spark는 스트리밍 데이터로의 전환을 편리하게 할 수 있다는 장점
```

**`※ 설계/고려사항`**

```
- 데이터 처리 후 정합성 체크를 위한 원본 데이터 보관
```

---

## [Data 분석]

#### Impala, Presto

```
- Impala : Apache Hadoop을 실행하는 Cluster에 저장된 데이터를 위한 오픈 소스 대규모 병렬 처리 SQL Query Engine
- Presto : Facebook이 개발한 분산 SQL Query Engine, 기존 분석도구인 하이브/맵리듀스에 비해 CPU 효율성과 대기 시간이 10배 빠르다고 발표
```

### ☞ Apache Lucene
```
- 색인과 검색 기능 제공, 자바 기반 검색 Library
```

### ☞ Elasticsearch 

```
- Lucene기반, 사이즈가 작은 데이터에 대한 속성검색/연관검색/실시간 검색에 용이함 (주요 커머스검색용)
- 자체 Master Node에서 관리, 강력한 API (RESTful 검색 및 분석 엔진)
```

**Apache Solr(솔라)**

```
- Lucene기반, 사이즈가 큰 데이터 검색에 용이에 문서 검색에 적합하나 색인주기가 느림 (주로 문서검색용)
- Apache ZooKeeper로 관리
```

**Scruid (Scala+Druid)**

```
- Scala에서 Druid Query를 쉽게 작성할 수있는 Open Source Library
- Library는 Query를 JSON으로 변환하고 사용자가 정의한 Case Class의 결과를 구문 분석
```

---

## [Data 시각화]

#### Apache Zeppelin

```
- 국내에서 주도하고 있는 오픈소스 프로젝트로써, Spark를 훨씬 더 편하고 강력하게 사용할 수 있게 해주는 도구
- 분석 코드 작성, 작업 스케쥴링, 데이터 시각화, 대시보드
- 여러 시스템에 대한 실행 결과를 얻기 위한 Interpreter들을 통해 Query 결과를 수집/시각화
```

### ☞ Kibana

```
- 로그 데이터 탐색에 사용되는 ELK Stack의 일부
- Elasticsearch 클러스터에 저장된 로그 데이터를 기반으로 대시 보드를 탐색, 시각화 및 구축 할 수있는 도구
- 주로 로그 메시지를 분석하는 데 사용
- YAML 구성 파일
- Elasticsearch에서만 작동하도록 설계되었으므로 다른 유형의 데이터 소스를 지원 안함
- 즉시 사용 가능한 경고 기능이 제공되지 않으며, noti를 추가하려면 Logz.io와 같은 호스팅 된 ELK 스택을 선택하거나 ElastAlert를 구현하거나 X-Pack을 사용해야 함
```

### ☞ Grafana

```
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

---

## [Data 관리]

### ☞ Airflow

```
Oozie -> Luigi -> Airflow, Workflow Tool로 Job 스케쥴링, 의존성에 따른 Task 관리
```

- `NiFi` : Process와 Process간 Data Flow Monitoring Tool
- `Ambari` : Cluster상 설치된 여러 솔루션들의 설정 값들을 관리, 각 요소들의 중지/시작을 Web Interface로 처리 가능