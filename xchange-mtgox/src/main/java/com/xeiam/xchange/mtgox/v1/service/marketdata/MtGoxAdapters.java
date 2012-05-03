/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.mtgox.v1.service.marketdata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.BigMoney;

import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.mtgox.v1.MtGoxProperties;
import com.xeiam.xchange.mtgox.v1.service.marketdata.dto.MtGoxOrder;

/**
 * Various adapters for converting from mtgox DTOs to XChange DTOs
 */
public class MtGoxAdapters {

  public static LimitOrder adaptOrder(MtGoxOrder mtGoxOrder, String currency, OrderType orderType) {

    LimitOrder limitOrder = new LimitOrder();
    limitOrder.setType(orderType);
    limitOrder.setTradableAmount(new BigDecimal((double) mtGoxOrder.getAmount_int() / MtGoxProperties.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR));
    limitOrder.setTradableIdentifier("BTC");
    limitOrder.setLimitPrice(BigMoney.parse(currency + " " + mtGoxOrder.getPrice()));
    return limitOrder;

  }

  public static List<LimitOrder> adaptOrders(List<MtGoxOrder> mtGoxOrders, String currency, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (MtGoxOrder mtGoxOrder : mtGoxOrders) {
      limitOrders.add(adaptOrder(mtGoxOrder, currency, orderType));
    }

    return limitOrders;
  }

}
