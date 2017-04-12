package com.snowcattle.game.db.service.jdbc.service.operation;

import com.snowcattle.game.db.common.annotation.AsyncEntityOperation;
import com.snowcattle.game.db.service.async.thread.AsyncDbOperation;
import com.snowcattle.game.db.service.jdbc.service.entity.impl.OrderService;

/**
 * Created by jwp on 2017/4/12.
 */
@AsyncEntityOperation
public class OrderServiceOperation extends AsyncDbOperation<OrderService> {

}
