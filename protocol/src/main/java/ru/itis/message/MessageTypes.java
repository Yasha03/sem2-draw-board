package ru.itis.message;

import ru.itis.exceptions.MessageTypeException;

import java.lang.reflect.Field;

public class MessageTypes {

    public static final byte DEFAULT_MESSAGE = 1;

    public static final byte REGISTER_FORM = 2;

    public static final byte REGISTER_ERROR = 3;

    public static final byte REGISTER_SUCCESS = 4;

    public static final byte LOGIN_ERROR = 5;

    public static final byte LOGIN_SUCCESS = 6;

    public static final byte LOGIN_FORM = 7;

    public static final byte CREATE_BOARD = 8;

    public static final byte DELETE_BOARD = 9;

    public static final byte CREATE_BOARD_SUCCESS = 10;

    public static final byte GET_ALL_BOARD = 11;

    public static final byte ADD_ELEMENT = 12;

    public static final byte SEND_ELEMENT_TO_CLIENT = 13;

    public static final byte GET_ALL_ELEMENT_BY_BOARD = 14;

    public static boolean typeIsExist(byte typeNumber) throws MessageTypeException {
        for(Field field : MessageTypes.class.getDeclaredFields()){
            try {
                if(field.getByte(new MessageTypes()) == typeNumber){
                    return true;
                }
            } catch (IllegalAccessException e) {
                throw new MessageTypeException("Error with existing message types");
            }
        }
        return false;
    }
}