 //控制层 
app.controller('goodsController' ,function($scope,$controller,$location ,goodsService,uploadService,itemCatService,typeTemplateService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(){	
		var id=$location.search()['id'];//获取参数值
		if(id==null){
			return;
		}
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;
				//向富文本编辑器中添加商品介绍
				editor.html($scope.entity.goodsDesc.introduction);
				//显示图片列表
				$scope.entity.goodsDesc.itemImages=JSON.parse($scope.entity.goodsDesc.itemImages);
				//显示扩展属性
				$scope.entity.goodsDesc.customAttributeItems=JSON.parse($scope.entity.goodsDesc.customAttributeItems);
				//显示规格
				$scope.entity.goodsDesc.specificationItems=JSON.parse($scope.entity.goodsDesc.specificationItems);
				//SKU规格列表转换
				for(var i=0;i<$scope.entity.itemList.length;i++){
					$scope.entity.itemList[i].spec=JSON.parse($scope.entity.itemList[i].spec);				
				}
			}
		);				
	}
	
	
	//根据规格名称和选项名称返回是否被勾选
	$scope.checkAttributeValue=function(specName,optionName){
		var items=$scope.entity.goodsDesc.specificationItems;
		//查询item中是否存在与specName相等的attributeName属性并返回
		var object=$scope.searchObjectByKey(items,'attributeName',specName);
		
		if(object==null){//为空 说明不存在属性名为specName的attributeName，便不用查找attributeValue直接返回false
			return false;
		}else{
			if(object.attributeValue.indexOf(optionName)>=0){//利用indexOf若存在optionName则会返回大于等于0的索引
				return true;
			}else{
				return false;
			}
		}
	}
	//保存
	$scope.save=function(){
		//提取文本编辑器的值
		$scope.entity.goodsDesc.introduction=editor.html();
		
		var serviceObject;//服务层对象
		if($scope.entity.goods.id!=null){//有id，则为修改
			serviceObject=goodsService.update($scope.entity);//修改
		}else{
			serviceObject=goodsService.add($scope.entity);//增加
		}
		
		serviceObject.success(
				function(response){
					if(response.success){
						alert("保存成功");
						$scope.entity={};
						editor.html("");
						location.href="goods.html";
					}else{
						alert(response.message);
					}
				}
		);
	}
	
	
	
	//新增
	$scope.add=function(){		
		$scope.entity.goodsDesc.introduction=editor.html();
		goodsService.add($scope.entity).success(
			function(response){
				if(response.success){
					alert("新增成功");
					$scope.entity={};
					$scope.entity={goods:{},goodsDesc:{itemImages:[],specificationItems:[]}};
					editor.html("");//清空富文本编辑器
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	/**
	 * 上传图片
	 */
	$scope.uploadFile=function(){
		uploadService.uploadFile().success(
			function(response) {
				if(response.success){//上传成功，取出url
					$scope.image_entity.url=response.message;//设置文件地址
					
				}else{
					alert(response.message);
				}
			}
		).error(function(){
			alert("上传发生错误");
		});
	}
	
	$scope.entity={goods:{},goodsDesc:{itemImages:[],specificationItems:[]}};//定义页面实体结构
	
	//添加图片列表
	$scope.add_image_entity=function(){
		$scope.entity.goodsDesc.itemImages.push($scope.image_entity);
	}
	
	//从列表中移除图片
	$scope.remove_image_entity=function(index){
		$scope.entity.goodsDesc.itemImages.splice(index,1);
	}
	
	//读取一级分类
	$scope.selectItemCat1List=function(){
		itemCatService.findByParentId(0).success(
				function(response){
					$scope.itemCat1List=response;
				}
		);
	}
	
	//读取二级分类
	$scope.$watch('entity.goods.category1Id',function(newValue,oldValue){
		//根据选择的值
		itemCatService.findByParentId(newValue).success(
				function(response){
					$scope.itemCat2List=response;
				}
		);
	});
	
	//读取三级分类
	$scope.$watch('entity.goods.category2Id',function(newValue,oldValue){
		//根据选择的值
		itemCatService.findByParentId(newValue).success(
				function(response){
					$scope.itemCat3List=response;
				}
		);
	});
	
	//读取模板id
	$scope.$watch('entity.goods.category3Id',function(newValue,oldValue){
		itemCatService.findOne(newValue).success(
				function(response){
					$scope.entity.goods.typeTemplateId=response.typeId;//更新模板ID
				}
		);
	});
    //模板ID选择后，更新品牌列表
	$scope.$watch('entity.goods.typeTemplateId',function(newValue,oldValue){
		typeTemplateService.findOne(newValue).success(
				function(response){
					$scope.typeTemplate=response;//获取类型模板	
					$scope.typeTemplate.brandIds=JSON.parse($scope.typeTemplate.brandIds);//品牌列表
					if($location.search()['id']==null){
						$scope.entity.goodsDesc.customAttributeItems=JSON.parse($scope.typeTemplate.customAttributeItems);//扩展属性
					}
					
				}
		);
		typeTemplateService.findSpecList(newValue).success(
				function(response){
					$scope.specList=response;
				}
		);
	});
	
	$scope.updateSpecAttribute=function($event,name,value){
		
		var object=$scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,'attributeName',name);
		if(object!=null){
			if($event.target.checked){
				object.attributeValue.push(value);
			}else{//取消勾选
				object.attributeValue.splice(object.attributeValue.indexOf(value),1);//移除选项
				//如果选项都取消了，将此条记录移除
				if(object.attributeValue.length==0){
					$scope.entity.goodsDesc.specificationItems.splice($scope.entity.goodsDesc.specificationItems.indexOf(object),1);
				}
			}
		}else{//没有改选项 新增加一个
			$scope.entity.goodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]});
		}
	}
	
	//创建SKU列表
	$scope.createItemList=function(){
		
		$scope.entity.itemList=[{spec:{},price:0,num:99999,status:'0',isDefault:'0'}];//初始值
		
		var items=$scope.entity.goodsDesc.specificationItems;
		
		for(var i=0;i<items.length;i++){
			$scope.entity.itemList=addColumn($scope.entity.itemList,items[i].attributeName,items[i].attributeValue);
		}
	}
	
	//添加列值
	addColumn=function(list,columnName,columnValues){
		var newList=[];//新的集合
		for(var i=0;i<list.length;i++){
			var oldRow=list[i];
			for(var j=0;j<columnValues.length;j++){
				var newRow=JSON.parse(JSON.stringify(oldRow));//深克隆
				newRow.spec[columnName]=columnValues[j];
				newList.push(newRow);
			}
		}
		return newList;
	}
	
	$scope.status1=['未审核','审核通过','审核未通过','关闭'];
	$scope.status2=['已下架','在售'];
	$scope.itemCatList=[];//商品分类列表
	
	//加载商品分类列表
	$scope.findItemCatList=function(){
		itemCatService.findAll().success(
				function(response){
					for(var i=0;i<response.length;i++){
						$scope.itemCatList[response[i].id]=response[i].name;
					}
				}
		);
	}
	
	//上下架
	$scope.updateMarketable=function(){
		goodsService.updateMarketable($scope.selectIds).success(
				function(response){
					
					if(response.success){
						$scope.reloadList();//刷新列表
						$scope.selectIds=[];
					}else{
						alert(response.message);
					}
					
				}
		);
	}
});	