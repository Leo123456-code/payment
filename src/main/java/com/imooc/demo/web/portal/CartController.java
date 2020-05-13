package com.imooc.demo.web.portal;

import com.imooc.demo.common.Const;
import com.imooc.demo.common.ResponseCode;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.pojo.User;
import com.imooc.demo.service.ICartAddService;
import com.imooc.demo.service.ICartService;
import com.imooc.demo.vo.CartProductVo;
import com.imooc.demo.vo.CartVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * ClassName: CartController
 * Description: TODO购物车模块控制层
 * Author: Leo
 * Date: 2020/3/15-21:03
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ICartService cartService;
    @Autowired
    private ICartAddService cartAddService;

    @RequestMapping("add.do")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse<CartVo> add(HttpSession session, Integer count, Integer productId)throws Exception{
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return cartService.add(user.getId(),count,productId);
    }

    @RequestMapping("update.do")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse<CartVo> update(HttpSession session, Integer count, Integer productId)throws Exception{
        /**
         * @Description //TODO 更新购物车
           @Author Leo
         * @Date 15:08 2020/3/16
         * @Param [session, count, productId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.vo.CartVo>
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return cartService.update(user.getId(),count,productId);
    }

    @RequestMapping("delete_product.do")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse<CartVo> deleteProduct(HttpSession session,String productIds)throws Exception{
        /**
         * @Description //TODO 删除购物车的商品
           @Author Leo
         * @Date 15:08 2020/3/16
         * @Param [session, count, productId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.vo.CartVo>
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return cartService.delete(user.getId(),productIds);
    }

    @RequestMapping("list.do")
    public ServerResponse<CartVo> list(HttpSession session,Integer productId){
        /**
         * @Description //TODO 查询购物车
           @Author Leo
         * @Date 15:08 2020/3/16
         * @Param [session, count, productId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.vo.CartVo>
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return cartService.list(user.getId(),productId);
    }

    @RequestMapping("cart.do")
    public ServerResponse<CartProductVo> cartOnlyOne(HttpSession session, Integer productId){
        /**
         * @Description //TODO 查询购物车里单个商品
           @Author Leo
         * @Date 15:08 2020/3/16
         * @Param [session, count, productId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.vo.CartVo>
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return cartAddService.onlyOne(user.getId(),productId);
    }

    @RequestMapping("select_all.do")
    public ServerResponse<CartVo> selectOrSelectAll(HttpSession session){
        /**
         * @Description //TODO 全选
           @Author Leo
         * @Date 15:08 2020/3/16
         * @Param [session, count, productId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.vo.CartVo>
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return cartService.selectOrUnSelectAll(user.getId(),Const.Cart.CHECKED);
    }

    @RequestMapping("un_select_all.do")
    public ServerResponse<CartVo> selectOrUnSelectAll(HttpSession session){
        /**
         * @Description //TODO 全不选
           @Author Leo
         * @Date 15:08 2020/3/16
         * @Param [session, count, productId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.vo.CartVo>
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return cartService.selectOrUnSelectAll(user.getId(),Const.Cart.UN_CHECKED);
    }

    @RequestMapping("select.do")
    public ServerResponse<CartVo> selectOrSelectOne(HttpSession session,Integer productId){
        /**
         * @Description //TODO 单独选
           @Author Leo
         * @Date 15:08 2020/3/16
         * @Param [session, count, productId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.vo.CartVo>
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return cartService.selectOrUnselectOne(user.getId(),productId,Const.Cart.CHECKED);
    }

    @RequestMapping("un_select.do")
    public ServerResponse<CartVo> selectOrUnSelectOne(HttpSession session,Integer productId){
        /**
         * @Description //TODO 单独反选
           @Author Leo
         * @Date 15:08 2020/3/16
         * @Param [session, count, productId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.vo.CartVo>
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return cartService.selectOrUnselectOne(user.getId(),productId,Const.Cart.UN_CHECKED);
    }

    @RequestMapping("get_cart_product_count.do")
    public ServerResponse<Integer> getCartProductCount(HttpSession session){
        /**
         * @Description //TODO 计算购物车商品的数量
           @Author Leo
         * @Date 15:08 2020/3/16
         * @Param [session, count, productId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.vo.CartVo>
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createBySuccess(0);
        }
        return cartService.selectCartProductCount(user.getId());
    }

    //单独选,单独反选
    //查询当前用户的购物车里面的产品数量,如果一个产品有10个,那么数量就是10.



}
