app.controller("brandController",function($scope,$http,$controller,brandService){
	
	
				$controller('baseController',{$scope:$scope});//继承
				
				//查询品牌列表
				$scope.findAll=function(){
					brandService.findAll().success(
						function(response){
							$scope.list=response;
						}
								
					);
				}	
					
				//分页
				$scope.findPage=function(page,size){
					brandService.findPage(page,size).success(
						function(response){
							$scope.list=response.rows;//显示当前页数据
							$scope.paginationConf.totalItems=response.total;//更新总记录数
						}
					);
				}
				//保存
				$scope.save=function(){
					var Object=null;
					if($scope.entity.id!=null){
						Object=brandService.update($scope.entity);
					}else{
						Object=brandService.add($scope.entity);
					}
					Object.success(
						function (response) {
							if(response.success){
								$scope.reloadList();//重新加载
							}else{
								alter(response.message);
							}
						}
					);
				}
				//查找by id
				$scope.findOne=function(id){
					brandService.findOne(id).success(
						function (response) {
							$scope.entity=response;
						}
					);
				}
				
				
				
				//删除
				$scope.deleteBrands=function(){
					brandService.deleteBrands($scope.selectIds).success(
						function (response) {
							if(response.success){
								$scope.reloadList();//重新加载
							}else{
								alter(response.message);
							}
						}
					);
				}
				
				$scope.searchEntity={};
				//搜索
				$scope.search=function(page,size){
					brandService.search(page,size,$scope.searchEntity).success(
						function(response){
							$scope.list=response.rows;//显示当前页数据
							$scope.paginationConf.totalItems=response.total;//更新总记录数
						}		
					);
				}
				
			});