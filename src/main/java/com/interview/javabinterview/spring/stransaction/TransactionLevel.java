package com.interview.javabinterview.spring.stransaction;

/**
 *
 * https://blog.csdn.net/soonfly/article/details/70305683
 *
 * PROPAGATION_REQUIRED:  如果当前线程已经在一个事务中，则加入该事务，否则新建一个事务。
 * PROPAGATION_SUPPORTS: 如果当前线程已经在一个事务中，则加入该事务，否则不使用事务。
 * MANDATORY(强制的)，如果当前线程已经在一个事务中，则加入该事务，否则抛出异常。
 * REQUIRES_NEW，无论如何都会创建一个新的事务，如果当前线程已经在一个事务中，则挂起当前事务，创建一个新的事务。
 * NOT_SUPPORTED，如果当前线程在一个事务中，则挂起事务。
 * NEVER，表示当前方法不应该运行在事务上下文中,如果当前正有一个事务在运行,则会抛出异常
 * NESTED, 执行一个嵌套事务，有点像REQUIRED，但是有些区别，在Mysql中是采用SAVEPOINT来实现的。
 *
 * @author Ju Baoquan
 * Created at  2020/6/1
 */
public class TransactionLevel {

    public static void main(String[] args) {
        method_1();// 七种传播行为
    }

    private static void method_1() {
        /**
         * PROPAGATION_REQUIRED:  表示该方法必须运行在事务中. 如果当前事务存在,方法将会在该事务中运行. 否则,回启动一个新事物.
         *
         * PROPAGATION_SUPPORTS:  表示该方法不需要事务上下文. 但是如果存在当前事务的话,该方法会在当前事务中运行. 如果不存则无事务运行.
         *
         * PROPAGATION_MANDATORY: 表示该方法必须在事务中运行. 如果当前事务不存在,则会抛出一个异常.
         *
         * PROPAGATION_REQUIRED_NEW: 表示该方法必须运行在它自己的事务中运行. 无论如何都会创建一个新的事务，如果当前线程已经在一个事务中，
         *                           则挂起当前事务，创建一个新的事务。
         *
         * PROPAGATION_NOT_SUPPORTED: 表示该方法不应该运行在事务中. 如果存在当前事务,在该方法运行期间,当前事务将被挂起
         *
         * PROPAGATION_NEVER: 表示当前方法不应该运行在事务中.如果当前正有一个事务在运行,则会抛异常.
         *
         * PROPAGATION_NESTED: 表示如果当前已经存在一个事务,那么该方法会在嵌套事务中运行. 嵌套的事务可以独立于当前事务进行单独提交或回滚.
         *                     如果当前事务不存在,那么其行为与PROPAGATION_REQUIRED一样
         *
         */
    }
}
