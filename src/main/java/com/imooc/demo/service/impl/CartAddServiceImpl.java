package com.imooc.demo.service.impl;

import com.imooc.demo.common.Const;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.dao.CartMapper;
import com.imooc.demo.dao.ProductMapper;
import com.imooc.demo.pojo.Cart;
import com.imooc.demo.pojo.Product;
import com.imooc.demo.service.ICartAddService;
import com.imooc.demo.util.BigDecimalUtil;
import com.imooc.demo.vo.CartProductVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * ClassName: CartAddServiceImpl
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/17-11:25
 * email 1437665365@qq.com
 */
@Service
@Slf4j
public class CartAddServiceImpl implements ICartAddService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse<CartProductVo> onlyOne(Integer userId, Integer productId) {
        /**
         * @Description //TODO 根据productId查询购物车单个商品
         @Author Leo
          * @Date 11:00 2020/3/17
         * @Param [userId, productId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.vo.CartProductVo>
         */
        Cart cart2 = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart2 == null) {
            return ServerResponse.createByErrorMessage("购物车不存在此商品");
        }
        List<Cart> cartList2 = cartMapper.selectListByUserIdAndProductId(userId,productId);
        CartProductVo cartProductVo = new CartProductVo();
        if (!StringUtils.isEmpty(cartList2)) {
            cartProductVo.setId(cart2.getId());
            cartProductVo.setUserId(cart2.getUserId());
            cartProductVo.setProductId(cart2.getProductId());
            Product product = productMapper.selectByPrimaryKey(productId);
            if (product != null) {
                cartProductVo.setProductMainImage(product.getMainImage());
                cartProductVo.setProductName(product.getName());
                cartProductVo.setProductSubtitles(product.getSubImages());
                cartProductVo.setProductStatus(product.getStatus());
                cartProductVo.setProductStock(product.getStock());
                cartProductVo.setProductPrice(product.getPrice());
                //判断库存
                int buyLimitCount = 0;
                if (product.getStock() >= cart2.getQuantity()) {
                    //库存充足时
                    buyLimitCount = cart2.getQuantity();
                    cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                } else {
                    buyLimitCount = product.getStock();
                    cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                    //购物车更新有效库存
                    Cart cartForQuantity = new Cart();
                    cartForQuantity.setId(cart2.getId());
                    cartForQuantity.setQuantity(buyLimitCount);
                    int rowCount = cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                }
                cartProductVo.setQuantity(buyLimitCount);
                //计算总价 单价*数量
                cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),
                        cartProductVo.getQuantity()));
                cartProductVo.setProductChecked(cart2.getChecked());
            }
        }
        return ServerResponse.createBySuccess(cartProductVo);
    }
}