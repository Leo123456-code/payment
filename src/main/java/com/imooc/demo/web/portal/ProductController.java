package com.imooc.demo.web.portal;

import com.github.pagehelper.PageInfo;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.service.IProductService;
import com.imooc.demo.vo.ProductDetailVo;
import com.imooc.demo.vo.ProductListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: ProductController
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/13-6:01
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService productService;

    //获取商品详情
    @RequestMapping("detail.do")
    public ServerResponse<ProductDetailVo> detail(Integer productId){
       return productService.getProductDetailVo(productId);
    }

    //前端搜素结果展示
    @RequestMapping("search_keyword.do")
    public ServerResponse<PageInfo> qianSearch(@RequestParam(value = "productName",required = false) String productName,
                                                              @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                                              @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                              @RequestParam(value = "pageSzie",defaultValue = "10") Integer pageSzie,
                                                              @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
        return productService.qianSearchGetProductproductNameCategory(productName,categoryId,pageNum,pageSzie,orderBy);
    }

    //前端搜素结果展示 ,动态SQL 排序
    @RequestMapping("search_keyword_order.do")
    public ServerResponse<PageInfo> qianSearchOrderBy(@RequestParam(value = "productName",required = false) String productName,
                                                      @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                                      @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSzie",defaultValue = "10") Integer pageSzie,
                                                      @RequestParam(value = "orderBy",defaultValue = "") String orderBy,
                                                      @RequestParam(value = "orderType",defaultValue = "") String orderType){
        return productService.qianSearchGetProductproductNameCategoryOrder(productName,categoryId,pageNum,pageSzie,orderBy,orderType);
    }


}
