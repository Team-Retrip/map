package com.retrip.map.infra.config

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.rest_client.RestClientTransport
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.ssl.SSLContexts
import org.elasticsearch.client.RestClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ElasticsearchConfig(
    @Value("\${elasticsearch.host:172.31.60.74}") private val host: String,
    @Value("\${elasticsearch.port:9200}") private val port: Int,
    @Value("\${elasticsearch.scheme:https}") private val scheme: String,
) {
    @Bean
    fun elasticsearchClient(): ElasticsearchClient {
        val credentialsProvider = BasicCredentialsProvider()
        credentialsProvider.setCredentials(
            AuthScope.ANY,
            UsernamePasswordCredentials("elastic", "test1234")
        )

        val sslContext = SSLContexts.custom()
            .loadTrustMaterial(null) { _, _ -> true }
            .build()

        val restClient = RestClient.builder(HttpHost(host, port, scheme))
            .setHttpClientConfigCallback { httpClientBuilder ->
                httpClientBuilder
                    .setSSLContext(sslContext)
                    .setDefaultCredentialsProvider(credentialsProvider)
            }
            .build()

        val transport = RestClientTransport(restClient, JacksonJsonpMapper())
        return ElasticsearchClient(transport)
    }
}
