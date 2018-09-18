package com.giraffe.framework.base.database.base.service;


import javax.persistence.Id;

import com.giraffe.framework.base.common.utils.EmptyUtil;
import com.giraffe.framework.base.common.utils.ReflectUtil;
import com.giraffe.framework.base.database.domain.returns.BaseResult;

public class BaseServiceUtil {


    /**
     * 根据操作结果创建BaseResult 对象
     *
     * @param modifyNum 修改成功的条数
     * @return ContainReturnValueResult<T>
     */
    public static BaseResult<Integer> createResultByOperationResult(Integer modifyNum) {
        if (EmptyUtil.isNotEmpty(modifyNum) && modifyNum > 0) {
            return new BaseResult<Integer>(modifyNum);
        } else {
            return new BaseResult<Integer>().setMessage("操作失败");
        }
    }

    public static <T> BaseResult<String> createResultByOperationResult(Integer modifyNum, T entity) {
        if (EmptyUtil.isNotEmpty(modifyNum) && modifyNum > 0) {
            Object id = ReflectUtil.getValueByAnnotation(entity, Id.class);
            if (EmptyUtil.isNotEmpty(id)) {
                return new BaseResult<String>(String.valueOf(id));
            } else {
                return new BaseResult<String>();
            }
        } else {
            return new BaseResult<String>().setMessage("操作失败");
        }
    }

}
