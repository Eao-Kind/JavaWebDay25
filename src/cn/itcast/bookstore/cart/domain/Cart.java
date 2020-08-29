package cn.itcast.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();

    public double getTotal() {
        // 合计
        BigDecimal total = new BigDecimal("0");
        for (CartItem cartItem : map.values()) {
            BigDecimal subtotal = new BigDecimal("" + cartItem.getSubtotal());
            total = total.add(subtotal);
        }
        return total.doubleValue();
    }

    /**
     * 增加条目
     *
     * @param cartItem
     */
    public void add(CartItem cartItem) {
        if (map.containsKey(cartItem.getBook().getBid())) {
            CartItem cartItem1 = map.get(cartItem.getBook().getBid());
            cartItem1.setCount(cartItem1.getCount() + cartItem.getCount()); //老+新 数目
            map.put(cartItem.getBook().getBid(), cartItem1);
        } else {
            map.put(cartItem.getBook().getBid(), cartItem);
        }
    }

    /**
     * 清空
     */
    public void clear() {
        map.clear();
    }

    public void delete(String bid) {
        map.remove(bid);
    }

    public Collection<CartItem> getCartItems() {
        return map.values();
    }
}
