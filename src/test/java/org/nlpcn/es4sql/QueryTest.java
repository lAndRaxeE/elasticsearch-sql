package org.nlpcn.es4sql;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.junit.Test;
import org.nlpcn.es4sql.exception.SqlParseException;

public class QueryTest {
	
	private SearchDao searchDao = new SearchDao("localhost", 9300) ;
	@Test
	public void likeTest() throws IOException, SqlParseException{
		SearchRequestBuilder select = searchDao.explan("select email from bank where age between 20 and 21 and email like '%c%' ");
		System.out.println(select);
	}
	
	@Test
	public void betweenTest() throws IOException, SqlParseException{
		SearchRequestBuilder select = searchDao.explan("select age from bank where age between 20 and 21 limit 3");
		System.out.println(select);
	}
	
	@Test
	public void notBetweenTest() throws IOException, SqlParseException{
		SearchRequestBuilder select = searchDao.explan("select age from bank where age not between 20 and 21 limit 3");
		System.out.println(select);
	}
	
	@Test
	public void inTest() throws IOException, SqlParseException{
		SearchRequestBuilder select = searchDao.explan("select age from bank where age  in (20,21) limit 3");
		System.out.println(select);
	}
	
	@Test
	public void notInTest() throws IOException, SqlParseException{
		SearchRequestBuilder select = searchDao.explan("select age from bank where age not in (20,21) limit 3");
		System.out.println(select);
	}
	
	
	@Test
	public void dateSearch() throws IOException, SqlParseException{
		SearchRequestBuilder select = searchDao.explan("select insert_time from online where insert_time<'2014-08-18' limit 3");
		System.out.println(select);
	}
	
	@Test
	public void dateBetweenSearch() throws IOException, SqlParseException{
		SearchRequestBuilder select = searchDao.explan("select insert_time from online where insert_time between '2014-08-18' and '2014-08-21' limit 3");
		System.out.println(select);
	}
	
	/**
	 * 是否存在查询
	 * @throws IOException
	 * @throws SqlParseException
	 */
	@Test
	public void missFilterSearch() throws IOException, SqlParseException{
		SearchRequestBuilder select = searchDao.explan("select insert_time from online where insert_time is not miss order by _score desc limit 10");
		System.out.println(select);
	}
	
	@Test
	public void missQuerySearch() throws IOException, SqlParseException{
		SearchRequestBuilder select = searchDao.explan("select insert_time from online where insert_time is not miss limit 10");
		System.out.println(select);
	}
	

	@Test
	public void boolQuerySearch() throws IOException, SqlParseException{
		SearchRequestBuilder select = searchDao.explan("select * from bank where (gender='m' and (age> 25 or account_number>5)) or (gender='w' and (age>30 or account_number < 8)) and email is not miss order by age,_score desc limit 10 ");
		System.out.println(select);
	}
	


	@Test
	public void countSearch() throws IOException, SqlParseException{
		SearchRequestBuilder select = searchDao.explan("select count(*) from bank where (gender='m' and (age> 25 or account_number>5)) or (gender='w' and (age>30 or account_number < 8)) and email is not miss");
		System.out.println(select);
	}
	
	/**
	 * table have '.' and type test
	 * @throws IOException
	 * @throws SqlParseException
	 */
	@Test
	public void searchTableTest() throws IOException, SqlParseException{
		SearchRequestBuilder select = searchDao.explan("select count(*) from doc/accounts,bank/doc limit 10");
		System.out.println(select);
	}
}
