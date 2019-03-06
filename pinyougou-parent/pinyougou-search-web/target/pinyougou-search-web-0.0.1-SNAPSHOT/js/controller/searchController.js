app.controller("searchController",function($scope,$location,searchService){

    //定义搜索对象的结构category:商品分类
    $scope.searchMap={'keywords':'','category':'','brand':'','spec':{},'price':'','pageNo':1,'pageSize':40,'sortField':'','sort':''};
    //搜索
    $scope.search=function(){
        $scope.searchMap.pageNo=parseInt($scope.searchMap.pageNo);
        searchService.search($scope.searchMap).success(
            function(response){
                $scope.resultMap=response;//返回搜索结果
                buildPageLabel();//调用
            }
        );
    }

    //添加搜索项 改变searchMap的值
    $scope.addSearchItem=function(key,value){
        if(key=='category' || key=='brand' || key=='price'){//如果用户点击的是分类或品牌、价格
            $scope.searchMap[key]=value;
        }else{ // 用户点击的是规格
            $scope.searchMap.spec[key]=value;
        }
        $scope.search();
    }

    //移除复合搜索项
    $scope.removeSearchItem=function(key){
         if(key=='category' || key=='brand' || key=='price'){//如果用户点击的是分类或品牌
              $scope.searchMap[key]="";
         }else{ // 用户点击的是规格
              delete $scope.searchMap.spec[key];//移除此属性
         }
         $scope.search();
    }

    //构建分页标签(totalPages为总页数)
    buildPageLabel=function(){
        $scope.pageLabel=[];//新增分页栏属性
        var maxPageNo=$scope.resultMap.totalPages;//得到最后页码
        var firstPage=1;//开始页码
        var lastPage=maxPageNo;//截至页码
        $scope.firstDot=true;//前面有点
        $scope.lastDot=true;//后面有点
        if($scope.resultMap.totalPages>5){//如果总页数大于5页，显示部分页码
            if($scope.searchMap.pageNo<=3){//如果当前页小于等于3
                lastPage=5;//前五页
                $scope.firstDot=false;
            }else if($scope.searchMap.pageNo>=$scope.resultMap.totalPages-2){//如果当前页码大于等于最大页码-2
                    //显示最后5页
                    firstPage=maxPageNo-4;//后五页
                    $scope.lastDot=false;
            }else{//显示当前页为中心的5页
                firstPage=$scope.searchMap.pageNo-2;
                lastPage=$scope.searchMap.pageNo+2;
            }
        }else{
            $scope.firstDot=false;
            $scope.lastDot=false;
        }
        for(var i=firstPage;i<=lastPage;i++){
            $scope.pageLabel.push(i);
        }
    }

    //根据页码查询
    $scope.queryByPage=function(pageNo){


        //页码验证
        if(pageNo<1 || pageNo>$scope.resultMap.totalPages){
            return;
        }
        $scope.searchMap.pageNo=pageNo;
        $scope.search();
    }

    //判断当前是否是第一页
    $scope.isTopPage=function(){
        if($scope.searchMap.pageNo==1){
            return true;
        }else{
            return false;
        }
    }
    //判断当前是否是最后一页
    $scope.isEndPage=function(){
            if($scope.searchMap.pageNo==$scope.resultMap.totalPages){
                return true;
            }else{
                return false;
            }
    }

    //设置排序规则
   $scope.sortSearch=function(sortField,sort){
        $scope.searchMap.sortField=sortField;
        $scope.searchMap.sort=sort;
        $scope.search();
   }

   //判断关键字是不是品牌
   $scope.keywordsIsBrand=function(){
        for(var i=0;i<$scope.resultMap.brandList.length;i++){
            if($scope.searchMap.keywords.indexOf($scope.resultMap.brandList[i].txt)>=0){//如果包含
                return true;
            }
        }
        return false;
   }

   //加载查询字符串
   $scope.loadkeywords=function(){
        $scope.searchMap.keywords=$location.search()['keywords'];
        $scope.search();
   }

});