package org.dbs.mydoc.config;

/*@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@ComponentScan("org.dbs.mydoc")
public class MyDocConfig extends WebMvcConfigurerAdapter {

	@Bean
	public RedisSessionRepository redisSessionRepository(RestTemplate restTemplate, Environment enviroment) {
		RedisSessionRepository redisSessionRepository = new RedisSessionRepository(restTemplate);
		redisSessionRepository.setUrltoGet(enviroment.getProperty("get_cache_url"), HttpMethod.GET);
		redisSessionRepository.setUrltoPut(enviroment.getProperty("put_cache_url"), HttpMethod.POST);
		redisSessionRepository.setUrltoDelete(enviroment.getProperty("remove_cache_url"), HttpMethod.DELETE);
		return redisSessionRepository;
	}
	
	
	@Bean
	public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(){
		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		poolingHttpClientConnectionManager.setMaxTotal(100);
		return poolingHttpClientConnectionManager;
	}
	
	
	@Bean(destroyMethod="close")
	public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager) {
		return null;       //custom().setConnectionManager(poolingHttpClientConnectionManager).build();
	}

	@Bean
	public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory(
	        CloseableHttpClient httpClient) {
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}
	

	*//**
	 * RestClient to access another rest web services
	 * 
	 * @param requestFactory
	 * @param errorHandler
	 * @return
	 *//*
	@Bean
	public RestTemplate restTemplate(HttpComponentsClientHttpRequestFactory requestFactory,
			ResponseErrorHandler errorHandler) {
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		restTemplate.setErrorHandler(errorHandler);
		return restTemplate;
	}*/
	
//}
