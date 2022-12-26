module protocol {
    requires lombok;
    requires com.google.gson;
    requires java.sql;
    requires spring.jdbc;
    requires spring.core;
    requires spring.context;
    requires org.postgresql.jdbc;


    opens ru.itis.serializers to com.google.gson;
    opens ru.itis.models to com.google.gson;

    exports ru.itis.message;
    exports ru.itis.serializers;
    exports ru.itis.models;
    exports ru.itis.exceptions;
}