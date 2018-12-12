 //控制层 
app.controller('goodsController' ,function($scope,$controller  ,$location ,goodsService,itemCatService){	
	
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
//				editor.html($scope.entity.goodsDesc.introduction);
				
//				$scope.entity.goods=JSON.parse($scope.entity.goods);
				
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
				$scope.findItemscatName($scope.entity.goods)
			}
		);				
	}
	//根据分类id查询分类名称
	$scope.findItemscatName=function(goods){
	
		itemCatService.findOne($scope.entity.goods.category1Id).success(
				function(response) {
					$scope.category1Name=response.name;
				}
		);
		itemCatService.findOne($scope.entity.goods.category2Id).success(
				function(response) {
					$scope.category2Name=response.name;
				}
		);
		itemCatService.findOne($scope.entity.goods.category3Id).success(
				function(response) {
					$scope.category3Name=response.name;
				}
		);
	}
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
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
    
	
	$scope.status=['未审核','审核通过','审核未通过','关闭'];
	
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
	
	
	//更改状态
	$scope.updateStatus=function(status){
			goodsService.updateStatus($scope.selectIds,status).success(
					function(response){
						if(response.success){//成功
							$scope.reloadList();//刷新列表
							alert(response.message);
							$scope.selectIds=[];//清空id集合
						}else{
							alert(response.message);
						}
					}
			);
	}
});	
