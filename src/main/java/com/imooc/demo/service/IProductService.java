package com.imooc.demo.service;

import com.github.pagehelper.PageInfo;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.pojo.Product;
import com.imooc.demo.vo.ProductDetailVo;
import com.imooc.demo.vo.ProductListVo;


/**
 * ClassName: IProductService
 * Description: TODO商品接口层
 * Author: Leo
 * Date: 2020/3/11-7:07
 * email 1437665365@qq.com
 */
public interface IProductService {
    //增加或更新商品
    ServerResponse saveOrUpdateProduct(Product product);
    //修改商品的销售状态
    ServerResponse setSaleStatus(Integer productId,Integer status);
    //获取商品详情
    ServerResponse<ProductDetailVo>  manageProductDetail(Integer productId);
    //分页查询所有记录
    ServerResponse getProductListPageHelper(int pageNum,int pageSize);
    //根据姓名或productId进行搜素,分页
    ServerResponse searshProductListVoPage(String productName,Integer productId,int pageNum, int pageSize);
    //根据姓名或productId进行搜素,不分页
    ServerResponse searshProductListVo(String productName,Integer productId);
    //前台
    //商品详情
    ServerResponse<ProductDetailVo> getProductDetailVo(Integer productId);
    //搜索
    ServerResponse<PageInfo> qianSearchGetProductproductNameCategory(String productName, Integer categoryId, Integer pageNum, Integer pageSize,String orderBy);
    //动态SQL 排序  前台搜索
    ServerResponse<PageInfo> qianSearchGetProductproductNameCategoryOrder(String productName, Integer categoryId, Integer pageNum, Integer pageSize, String orderBy, String orderType);

}
