package com.interview.javabinterview.mybatis.mybatis;

import com.interview.javabinterview.mybatis.mybatis.entity.Blog;
import com.interview.javabinterview.mybatis.mybatis.mapper.BlogMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class Description:
 * <p>
 * https://blog.csdn.net/tianshouzhi/article/details/103922847
 *
 * @author baoquan.Ju
 * Created at  2020/10/20
 */
public class MyBatisTest {

    /***
     * SqlSessionFactory:
     *  是MyBatis的核心类，它是单个数据库映射关系经过编译后的内存镜像。
     *  主要功能是提供用于操作数据库的SqlSession。
     *
     *  SqlSession:
     *  是MyBatis的关键对象，是持久化操作的独享，类似于jdbc中的Connection，
     *  它是应用程序与持久层之间执行交互操作的一个单线程对象。
     *  SqlSession对象包含全部的以数据库为背景的SQL操作方法，底层封装jdbc连接，
     *  可以用SqlSession实例来直接执行被映射的SQL语句。
     *
     *  注意:SqlSession应该是线程私有的，因为它不具备线程安全性。
     *
     *  mybaits中 mapper 的工作原理:
     *  原理: 当接口方法执行时，首先通过反射拿到当前接口的全路径当做namespace，然后把执行的方法名当成id，拼接成namespace.id，
     *  最后在xml映射文件中寻找对应的sql。
     *  1.接口的全路径就是映射文件的namespace属性值
     *  2.接口中定义的方法，方法名与映射文件中<insert>、<select>、<delete>、<update>等元素的id属性值相同
     *
     * ************************************************************************************************************
     * 深入理解mybatis和spring 的整合:
     *
     *  1.结构如下:
     *  -----------spring-----------mybatis与spring整合后，可以直接在业务层通过@Autowired注解注入Mapper，也会利用spring提供的事务管理机制
     *  -------mybatis-spring-------用于spring-mybatis整合(mybatis本身可以单独使用，如果需要与spring进行整合，则需要额外引入mybatis-spring)
     *  -----------mybatis----------orm框架,避免直接操作JDBC-API
     *  ---------datasource---------将建立的mysql连接维护到一个连接池中，进行链接复用。典型的连接池如druid、c3p0、tomcat-jdbc、dbcp2、hicaricp等
     *  ----mysql-connector-java----mysql数据库驱动，用于与mysql建立真实连接
     *  -----------数据库------------
     *
     * 2.单独使用mybatis:
     * String resource = "org/mybatis/example/mybatis-config.xml";
     * InputStream in = Resources.getResourceAsStream(resource);
     * SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
     * SqlSession session = sqlSessionFactory.openSession();
     * try {
     *   BlogMapper mapper = session.getMapper(BlogMapper.class);
     *   Blog blog = mapper.selectBlog(1);
     * } finally {
     *   session.close();
     * }
     *
     * 3.SqlSessionFactoryBean
     * SqlSessionFactory是mybatis的核心，当与spring进行整合时，我们使用mybatis-spring提供的SqlSessionFactoryBean
     * 来创建其实例，SqlSessionFactoryBean实现了FactoryBean 接口。SqlSessionFactoryBean的配置有2种风格：
     *
     *  --保留mybatis的核心配置文件
     *  --不保留mybatis的核心配置文件
     *
     *  在mybatis与spring整合后, 通常我们不会再直接使用SqlSessionFactory，这种方式用起来比较麻烦。
     *
     * mybatis-spring提供了易于使用的类，如:
     *  SqlSessionTemplate、
     *  SqlSessionDaoSupport等，
     * 当然也有大多数读者都已经非常熟悉的:
     *  MapperScannerConfigurer 、
     *  MapperFactoryBean等
     *
     * 4.SqlSessionTemplate
     *
     * SqlSessionTemplate 是 mybatis-spring 的核心，其实现了SqlSession接口，且线程安全。
     * 使用了SqlSessionTemplate之后，我们不再需要通过SqlSessionFactory.openSession()方法来创建SqlSession实例；
     * 使用完成之后，也不要调用SqlSession.close()方法进行关闭。另外，对于事务，
     * SqlSessionTemplate 将会保证使用的 SqlSession 是和当前 Spring 的事务相关的
     * SqlSessionTemplate依赖于SqlSessionFactory，其配置方式如下所示：
     *
     * <bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">
     *      <constructor-arg index="0" ref="sqlSessionFactory"/>
     * </bean>
     * <bean id="userDao" class="com.tianshouzhi.mybatis.dao.UserDao"/>
     *
     * 之后我们可以在UserD类中直接进行注入。SqlSessionTemplate 是线程安全的, 可以被多个 DAO 所共享使用，以下是一个示例：
     *
     * public class UserDao {
     *     private static String NAMESPACE = "com.baoquan.ju.UserMapper";
     *     @Autowired
     *     SqlSessionTemplate sqlSession;
     *     public User selectById(int id) {
     *         User user = sqlSession.selectOne(NAMESPACE + ".selectById",id);
     *         return user;
     *     }
     * }
     *
     * 5.SqlSessionDaoSupport
     * 除了直接注入SqlSessionTemplate，也可以编写一个Dao类继承SqlSessionDaoSupport，
     * 调用其getSqlSession()方法来返回 SqlSessionTemplate。
     *
     * SqlSessionDaoSupport 需要一个 sqlSessionFactory 或 sqlSessionTemplate 属性来设置 。
     * 如果两者都被设置了 , 那么SqlSessionFactory是被忽略的。
     *
     * 事实上，如果你提供的是一个SqlSessionFactory，SqlSessionDaoSupport内部也会使
     * 用其来构造一个SqlSessionTemplate实例
     *
     * <bean id="userDao" class="com.tianshouzhi.zebracost.dao.UserDao">
     *       <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
     *  </bean>
     *  或：
     * <bean id="userDao" class="com.tianshouzhi.zebracost.dao.UserDao">
     *      <property name="sqlSessionTemplate" ref="sqlSessionTemplate"/>
     * </bean>
     *
     * 由于我们的UserDao类继承了SqlSessionDaoSupport，所以你可以在UserDao类中这样使用:
     *
     * public class UserDao extends SqlSessionDaoSupport{
     *     private static String NAMESPACE = "com.tianshouzhi.zebracost.UserMapper";
     *     public User selectById(int id) {
     *         User user = getSqlSession().selectOne(NAMESPACE + ".selectById",id);
     *         return user;
     *     }
     * }
     *
     * 6.MapperFactoryBean
     * 无论是使用SqlSessionTemplate，还是继承SqlSessionDaoSupport，我们都需要手工编写DAO类的代码。
     * 熟悉mybatis同学知道，SqlSession有一个getMapper()方法，可以让我们通过映射器接口的方式来使用mybatis。如：
     * SqlSession session = sqlSessionFactory.openSession();
     * try {
     *   UserMapper mapper = session.getMapper(UserMapper.class);
     *   User user = mapper.selectById(1);
     * } finally {
     *   session.close();
     * }
     *
     * 但是在与spring进行整合时，是否有更加简单的使用方法呢？能否通过@Autowired注解直接注入Mapper呢？我们期望的使用方式是这样：
     * public class UserService {
     *     @Autowired
     *     private UserMapper userMapper;
     *     public void insert(User user){
     *         userMapper.insert(user);
     *     }
     * }
     *
     * 在没有进行任何配置的情况下，直接这样操作显然是会报错的，因为UserMapper是一个接口，且不是spring管理的bean，
     * 因此无法直接注入。
     *
     *  这个时候，MapperFactoryBean则可以登场了，通过如下配置，MapperFactoryBean会针对UserMapper接口创建一个代理，
     *  并将其变成spring的一个bean。***底层是通过对UserMapper接口进行JDK动态代理，内部使用SqlSessionTemplate完成CRUD操作***
     *  <bean id="userMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
     *   <property name="mapperInterface" value="com.tianshouzhi.mybatis.mapper.UserMapper" />
     *   <property name="sqlSessionFactory" ref="sqlSessionFactory" />
     * </bean>
     *
     * MapperFactoryBean继承了SqlSessionDaoSupport类，这也是为什么我们先介绍SqlSessionDaoSupport的原因。
     *
     * 通过上述配置，我们就可以在一个业务bean中直接注入UserMapper接口了：
     * public class UserService{
     *    @Autowired
     *    private UserMapper userMapper;
     *    ...
     * }
     *
     *
     * 7.MapperScannerConfigurer
     * 通过MapperFactoryBean配置，已经是mybatis与spring进行时理想的方式了，我们可以简单的通过@Autowired注解进行注入。
     *
     * 但是如果有许多的Mapper接口要配置，针对每个接口都配置一个MapperFactoryBean，会使得我们的配置文件很臃肿。
     * 关于这一点，mybatis-spring包中提供了MapperScannerConfigurer来帮助你解决这个问题。
     *
     * MapperScannerConfigurer可以指定扫描某个包，为这个包下的所有Mapper接口，
     * 在Spring上下文中都MapperFactoryBean。
     *
     * <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
     *   <property name="basePackage" value="org.tianshouzhi.mybatis.user.mappers" />
     * </bean>
     *
     * basePackage 属性是用于指定Mapper接口的包路径。如果的Mapper接口位于不同的包下，可以使用分号”;”或者逗号”,”进行分割。如：
     *
     * <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
     *   <property name="basePackage" value="aa.bb.mapper;aa.dd.mapper" />
     * </bean>
     *
     * 注意，如果某个路径还包含子包，子包中的Mapper接口递归地被搜索到。因此对于上述配置，我们可以通过公共的包名进行简化。如：
     *
     *  <property name="basePackage" value="aa" />
     *
     * 如果指定的公共的包名下面还包含了一些其他的接口，这些接口是你作为其他用途使用到的，并不能作为mybatis的Mapper接口来使用。
     * 此时，你可以通过markerInterface属性或者annotationClass属性来进行过滤。
     *
     * 方式一:markerInterface属性，顾名思义，通过一个标记接口(接口中不需要定义任何方法)，来对Mapper映射器接口进行过滤，如：
     *
     * public interface MapperInterface{}
     *
     * 接着，你需要将你的映射器接口继承MapperInterface，如：
     *
     * public interface UserMapper implements MapperInterface{
     *    public void insert(User user)；
     * }
     *
     * 此时你可以为MapperScannerConfigurer指定只有继承了MapperInterface接口的子接口，才为其自动注册MapperFactoryBean，如：
     *
     * <property name="markerInterface" value=“com.tianshouzhi.mybatis.MybatisMapperInterface"/>
     *
     * 方式二:annotationClass属性的作用是类似的，只不过其是根据注解进行过滤。
     * 你不需要自定义注解，mybatis已经提供了一个@Mapper注解直接使用即可。配置如下：
     *
     * <property name="annotationClass" value="org.apache.ibatis.annotations.Mapper"/>
     *
     * 如果同时指定了markerInterface和annotationClass属性，那么只有同时满足这两个条件的接口才会被注册为MapperFactoryBean。
     *
     * 细心点可能意识到了，前面配置MapperFactoryBean的时候，我们需要为其指定SqlSessionFactory，或者SqlSessionTemplate。
     *
     * 现在通过MapperScannerConfigurer来自动注册MapperFactoryBean，
     * 我们可以为MapperScannerConfigurer指定sqlSessionFactory或sqlSessionTemplate
     * ，然后由MapperScannerConfigurer在内部设置到MapperFactoryBean中，如下：
     *
     * <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
     * <!--<property name="sqlSessionTemplate" ref="sqlSessionTemplate"/>-->
     */

    private SqlSessionFactory sqlSessionFactory;

    public void initSqlSessionFactory() {
        String resource = "..../mybatis-config.xml";
        try {
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            sqlSessionFactory = builder.build(Resources.getResourceAsStream(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Blog query_1(long id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            return sqlSession.selectOne("ocom.interview.javabinterview.mybatis.mybatis.BlogMapper.selectBlog", id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return null;
    }

    public Blog query_2() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            return (Blog) mapper.selectBlog(101);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return null;
    }
}

