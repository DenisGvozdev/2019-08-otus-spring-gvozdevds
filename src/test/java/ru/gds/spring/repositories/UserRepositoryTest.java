package ru.gds.spring.repositories;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.gds.spring.mongo.domain.Role;

import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
class UserRepositoryTest {

//    @Autowired
//    MongoTemplate mongoTemplate;
//
//    @Test
//    void saveTest() {
//
//        DBObject user = BasicDBObjectBuilder.start()
//                .add("username", "petrovpp")
//                .add("password", "password")
//                .add("email", "petr@mail.ru")
//                .add("phone", "+79672522000")
//                .add("firstName", "Петров")
//                .add("secondName", "Петр")
//                .add("thirdName", "Васильевич")
//                .add("roles", getRoleList())
//                .get();
//
//        mongoTemplate.save(user, "users");
//
//        List<DBObject> list = mongoTemplate.findAll(DBObject.class, "users");
//        assumeTrue(list.size() > 0);
//    }
//
//    private List<Role> getRoleList() {
//        Query query = Query.query(Criteria.where("role").is("ROLE_WRITE"));
//        return mongoTemplate.find(query, Role.class);
//    }
}
