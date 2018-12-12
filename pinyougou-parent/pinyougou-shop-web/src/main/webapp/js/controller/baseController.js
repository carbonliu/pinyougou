//基本控制层
app.controller('baseController',function($scope){
	//分页控件配置currentPage:当前页   totalItems :总记录数  itemsPerPage:每页记录数  perPageOptions :分页选项  onChange:当页码变更后自动触发的方法 
	$scope.paginationConf = {
		currentPage: 1,
		totalItems: 10,
		itemsPerPage:5,
		perPageOptions: [5, 10, 20, 30, 40],
		onChange: function(){
			$scope.reloadList();
		}
	};
	
	//刷新列表
	$scope.reloadList=function(){
		$scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
	}
	
	//被选中的品牌id集合
	$scope.selectIds=[];
	
	//复选框选择状态判断
	$scope.updateSelection=function($event,id){
		if($event.target.checked){
			$scope.selectIds.push(id);//push向集合添加元素
		}else{
			var index=$scope.selectIds.indexOf(id);//查找值的位置
			$scope.selectIds.splice(index,1);//参数1：移除的位置 参数2：
		}
	}
	
	$scope.jsonToString=function(jsonString,key){
			
			var json= JSON.parse(jsonString);
			var value="";
			
			for(var i=0;i<json.length;i++){
				if(i>0){
					value+=",";
				}			
				value +=json[i][key];			
			}
					
			return value;
	}
	
	//从集合中按照key查询对象
	$scope.searchObjectByKey=function(list,key,keyValue){
		
		for(var i=0;i<list.length;i++){
			
			if(list[i][key]==keyValue){
				
				return list[i];
			}
		}
		return null;
	}
});