package com.mongodb.spring;

import com.mongodb.client.result.UpdateResult;
import com.mongodb.spring.dao.PersonDao;
import com.mongodb.pojo.Address;
import com.mongodb.pojo.Person;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-04-02 9:17
 * @current mongodb-com.mongo
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTestPersonDao {

    @Autowired
    private PersonDao personDao;

    /*
     * 新增
     * */
    @Test
    public void testSave(){
        Person person =new Person(ObjectId.get(), "张三", 20, new Address("人民路", "上海市", "666666"));
        System.out.println(this.personDao.savePerson(person));;
    }

    @Test
    public void testQuery() {
        List<Person> personList = this.personDao.queryPersonListByName("张三");
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    @Test
    public void testQueryByPage(){
        List<Person> personList = this.personDao.queryPersonListByPage(1,3);

        for (Person person : personList) {
            System.out.println(person);
        }
    }

    @Test
    public void testUpdate() {
        Person person = new Person();
        person.setId(new ObjectId("60667404d8d68907198c10f3"));
        person.setAge(30);
        UpdateResult updateResult = this.personDao.update(person);

        System.out.println(updateResult);
    }

    @Test
    public void testDelete(){
        System.out.println(this.personDao.deleteResultById("6065d8edaccf9851f047b2d3"));
    }

}
