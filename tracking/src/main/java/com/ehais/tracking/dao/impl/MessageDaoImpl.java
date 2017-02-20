package com.ehais.tracking.dao.impl;

import org.springframework.stereotype.Repository;

import com.ehais.tracking.dao.MessageDao;
import com.ehais.tracking.entity.Message;


@Repository("messageDao")
public class MessageDaoImpl extends CommonDaoImpl<Message> implements MessageDao{

}
