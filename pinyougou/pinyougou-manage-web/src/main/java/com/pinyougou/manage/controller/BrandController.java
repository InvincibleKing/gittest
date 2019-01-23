package com.pinyougou.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/brand")
/**
 * @RestController 相当于@Controller和@ResponseBody
 */
@RestController
public class BrandController {

    /**
     * 引用阿里的注解相当于AutoWriter
     */
    @Reference
    private BrandService brandService;

    @GetMapping("/testPage")
    public List<TbBrand> testPage(Integer page,Integer rows){
        return (List<TbBrand>) brandService.findPage(page,rows).getRows();
    }

    /**
     * @GetMapping相当于get方法与@RequestMappering
     */
    @GetMapping("/findAll")
    public List<TbBrand> findAll(){
        return brandService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult findPage(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "rows",defaultValue = "10") Integer rows){
        return brandService.findPage(page,rows);
    }

    @PostMapping("/add")
    public Result add(@RequestBody TbBrand brand){
        try{
            brandService.add(brand);
            return Result.ok("新增成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail("新增失败");
    }
}
