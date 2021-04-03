package com.mongodb.spring.dao;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-04-02 9:12
 * @current mongodb-com.mongodb.spring.dao
 */
@Component
public class PersonDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public Person savePerson(Person person){
        //默认集合名与实体名同名
        Person savedPerson = this.mongoTemplate.save(person);
        return savedPerson;
    }

    public List<Person> queryPersonListByName(String name) {
        Query query = Query.query(Criteria.where("name").is(name));

        return this.mongoTemplate.find(query,Person.class);
    }


    /*
    * 分页查询
    * */
    public List<Person> queryPersonListByPage(Integer page, Integer rows) {
        Query query = new Query().limit(rows).skip((page - 1) * rows);
        return this.mongoTemplate.find(query, Person.class);
    }

    /*
    * 更新数据
    * */
    public UpdateResult update(Person person){
        Query query = Query.query(Criteria.where("id").is(person.getId()));

        Update update = Update.update("age",person.getAge());

        return this.mongoTemplate.updateMulti(query,update,Person.class);
    }

    /*
    * 删除数据
    * */
    public DeleteResult deleteResultById(String id){
        Query query = Query.query(Criteria.where("id").is(id));

        return this.mongoTemplate.remove(query,Person.class);
    }
}
