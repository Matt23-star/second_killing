package com.example.secondkill.service.impl;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.pojo.KillInformation;
import com.example.secondkill.entity.pojo.ProductInformation;
import com.example.secondkill.mapper.Product_informationMapper;
import com.example.secondkill.service.IProduct_informationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondkill.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */
@Service
public class Product_informationServiceImpl extends ServiceImpl<Product_informationMapper, ProductInformation> implements IProduct_informationService {

    @Autowired
    private Product_informationMapper productInformationMapper;

    @Override
    public Result addProduct(ProductInformation productInformation) {
        if(productInformation==null){
            return ResultUtils.error(new ResultMessage(400,"产品数据为空，添加失败"));
        }
        if(productInformationMapper.insert(productInformation)<=0){
            return ResultUtils.error(new ResultMessage(400,"添加失败"));
        }
        return ResultUtils.success(new ResultMessage(200, "Add Product Successfully"),null);
    }

    @Override
    public Result deleteProduct(String productId) {
        if(productId==null){
            return ResultUtils.error(new ResultMessage(400,"产品id为空，删除失败"));
        }
        int i = productInformationMapper.deleteById(productId);
        if (i <= 0) return ResultUtils.error
                (new ResultMessage
                        (412, "Delete Failed"));
        else return ResultUtils.success("Deleted successfully.");
    }

    @Override
    public Result updateProduct(ProductInformation productInformation) {
        if(productInformation==null){
            return ResultUtils.error(new ResultMessage(400,"产品信息为空，更新失败"));
        }
        int i = productInformationMapper.updateById(productInformation);
        if (i <= 0) return ResultUtils.error
                (new ResultMessage
                        (411, "Update Failed"));
        else return ResultUtils.success("Updated successfully.");
    }

    @Override
    public Result selectProductList(String colName, String value, String orderBy, String aOrD, int from, int limit) {
        if (value == null || value.equals("*")) value = "";
        final List<ProductInformation> productInformationList
                = productInformationMapper.universalProductSelect(colName, value, orderBy, aOrD, from, limit);
        return ResultUtils.success(new ResultMessage(200,"查询产品成功"),productInformationList);
    }
}
