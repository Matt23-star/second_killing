package com.example.secondkill.service;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.pojo.ProductInformation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */
public interface IProduct_informationService extends IService<ProductInformation> {

    Result addProduct(ProductInformation productInformation);

    Result deleteProduct(String productId);

    Result updateProduct(ProductInformation productInformation);

    Result selectProductList(String colName, String value, String orderBy, String aOrD, int from, int limit);
}
