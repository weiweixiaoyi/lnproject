package com.example.guo.lnproject.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class CookbookEntity {

    /**
     * msg : success
     * result : {"curPage":1,"list":[{"ctgIds":["0010001009","0010001025","0010001063"],"ctgTitles":"汤粥,煮,养生","menuId":"00100010090000025824","name":"深秋最佳补水汤","recipe":{"img":"http://f2.mob.com/null/2015/08/19/1439993091463.jpg","ingredients":"[\"排骨 鱼圆 野三珍菌 青菜 玉米\",\"\u200b盐 鸡精 料酒  \"]","method":"[{\"img\":\"http://f2.mob.com/null/2015/08/19/1439993092001.jpg\",\"step\":\"1.先把玉米切断。\"},{\"img\":\"http://f2.mob.com/null/2015/08/19/1439993092659.jpg\",\"step\":\"2.处理排骨。把水烧开放入排骨烧2分钟，然后把水倒掉把排骨冲洗干净。这样一来可以去除排骨里的杂物，二来焯过水的排骨冷水一击后再烧肉质更紧实，口感会更好。\"},{\"img\":\"http://f2.mob.com/null/2015/08/19/1439993093178.jpg\",\"step\":\"3.把排骨放入砂锅中加清水和料酒烧开后中火烧20分钟左右\u200b加入切好的玉米块，中火烧10分钟。加入泡好的野三珍菌。我后来居然放的是香菇，哈哈，那也没有关系， 一样好吃的啊。大火烧两分钟。.加入鱼圆。\"},{\"img\":\"http://f2.mob.com/null/2015/08/19/1439993094124.jpg\",\"step\":\"4.接着马上放青菜，看看很多，一烧就会变少的。最后加入盐和鸡精，美味的汤水就做好了。这碗汤里每个人都能找到自己喜欢吃的菜，而且这个汤水有了排骨，味道更是变的鲜美无比。里面的蔬菜有了排骨的滋润吃起来更加美味。              \"}]","sumary":"深秋最佳补水汤  ","title":"深秋最佳补水汤"},"thumbnail":"http://f2.mob.com/null/2015/08/19/1439992992326.jpg"}],"total":1}
     * retCode : 200
     */

    private String msg;
    /**
     * curPage : 1
     * list : [{"ctgIds":["0010001009","0010001025","0010001063"],"ctgTitles":"汤粥,煮,养生","menuId":"00100010090000025824","name":"深秋最佳补水汤","recipe":{"img":"http://f2.mob.com/null/2015/08/19/1439993091463.jpg","ingredients":"[\"排骨 鱼圆 野三珍菌 青菜 玉米\",\"\u200b盐 鸡精 料酒  \"]","method":"[{\"img\":\"http://f2.mob.com/null/2015/08/19/1439993092001.jpg\",\"step\":\"1.先把玉米切断。\"},{\"img\":\"http://f2.mob.com/null/2015/08/19/1439993092659.jpg\",\"step\":\"2.处理排骨。把水烧开放入排骨烧2分钟，然后把水倒掉把排骨冲洗干净。这样一来可以去除排骨里的杂物，二来焯过水的排骨冷水一击后再烧肉质更紧实，口感会更好。\"},{\"img\":\"http://f2.mob.com/null/2015/08/19/1439993093178.jpg\",\"step\":\"3.把排骨放入砂锅中加清水和料酒烧开后中火烧20分钟左右\u200b加入切好的玉米块，中火烧10分钟。加入泡好的野三珍菌。我后来居然放的是香菇，哈哈，那也没有关系， 一样好吃的啊。大火烧两分钟。.加入鱼圆。\"},{\"img\":\"http://f2.mob.com/null/2015/08/19/1439993094124.jpg\",\"step\":\"4.接着马上放青菜，看看很多，一烧就会变少的。最后加入盐和鸡精，美味的汤水就做好了。这碗汤里每个人都能找到自己喜欢吃的菜，而且这个汤水有了排骨，味道更是变的鲜美无比。里面的蔬菜有了排骨的滋润吃起来更加美味。              \"}]","sumary":"深秋最佳补水汤  ","title":"深秋最佳补水汤"},"thumbnail":"http://f2.mob.com/null/2015/08/19/1439992992326.jpg"}]
     * total : 1
     */

    private ResultEntity result;
    private String retCode;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public ResultEntity getResult() {
        return result;
    }

    public String getRetCode() {
        return retCode;
    }

    public static class ResultEntity {
        private int curPage;
        private int total;
        /**
         * ctgIds : ["0010001009","0010001025","0010001063"]
         * ctgTitles : 汤粥,煮,养生
         * menuId : 00100010090000025824
         * name : 深秋最佳补水汤
         * recipe : {"img":"http://f2.mob.com/null/2015/08/19/1439993091463.jpg","ingredients":"[\"排骨 鱼圆 野三珍菌 青菜 玉米\",\"\u200b盐 鸡精 料酒  \"]","method":"[{\"img\":\"http://f2.mob.com/null/2015/08/19/1439993092001.jpg\",\"step\":\"1.先把玉米切断。\"},{\"img\":\"http://f2.mob.com/null/2015/08/19/1439993092659.jpg\",\"step\":\"2.处理排骨。把水烧开放入排骨烧2分钟，然后把水倒掉把排骨冲洗干净。这样一来可以去除排骨里的杂物，二来焯过水的排骨冷水一击后再烧肉质更紧实，口感会更好。\"},{\"img\":\"http://f2.mob.com/null/2015/08/19/1439993093178.jpg\",\"step\":\"3.把排骨放入砂锅中加清水和料酒烧开后中火烧20分钟左右\u200b加入切好的玉米块，中火烧10分钟。加入泡好的野三珍菌。我后来居然放的是香菇，哈哈，那也没有关系， 一样好吃的啊。大火烧两分钟。.加入鱼圆。\"},{\"img\":\"http://f2.mob.com/null/2015/08/19/1439993094124.jpg\",\"step\":\"4.接着马上放青菜，看看很多，一烧就会变少的。最后加入盐和鸡精，美味的汤水就做好了。这碗汤里每个人都能找到自己喜欢吃的菜，而且这个汤水有了排骨，味道更是变的鲜美无比。里面的蔬菜有了排骨的滋润吃起来更加美味。              \"}]","sumary":"深秋最佳补水汤  ","title":"深秋最佳补水汤"}
         * thumbnail : http://f2.mob.com/null/2015/08/19/1439992992326.jpg
         */

        private List<ListEntity> list;

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public int getCurPage() {
            return curPage;
        }

        public int getTotal() {
            return total;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            private String ctgTitles;
            private String menuId;
            private String name;
            /**
             * img : http://f2.mob.com/null/2015/08/19/1439993091463.jpg
             * ingredients : ["排骨 鱼圆 野三珍菌 青菜 玉米","​盐 鸡精 料酒  "]
             * method : [{"img":"http://f2.mob.com/null/2015/08/19/1439993092001.jpg","step":"1.先把玉米切断。"},{"img":"http://f2.mob.com/null/2015/08/19/1439993092659.jpg","step":"2.处理排骨。把水烧开放入排骨烧2分钟，然后把水倒掉把排骨冲洗干净。这样一来可以去除排骨里的杂物，二来焯过水的排骨冷水一击后再烧肉质更紧实，口感会更好。"},{"img":"http://f2.mob.com/null/2015/08/19/1439993093178.jpg","step":"3.把排骨放入砂锅中加清水和料酒烧开后中火烧20分钟左右​加入切好的玉米块，中火烧10分钟。加入泡好的野三珍菌。我后来居然放的是香菇，哈哈，那也没有关系， 一样好吃的啊。大火烧两分钟。.加入鱼圆。"},{"img":"http://f2.mob.com/null/2015/08/19/1439993094124.jpg","step":"4.接着马上放青菜，看看很多，一烧就会变少的。最后加入盐和鸡精，美味的汤水就做好了。这碗汤里每个人都能找到自己喜欢吃的菜，而且这个汤水有了排骨，味道更是变的鲜美无比。里面的蔬菜有了排骨的滋润吃起来更加美味。              "}]
             * sumary : 深秋最佳补水汤  
             * title : 深秋最佳补水汤
             */

            private RecipeEntity recipe;
            private String thumbnail;
            private List<String> ctgIds;

            public void setCtgTitles(String ctgTitles) {
                this.ctgTitles = ctgTitles;
            }

            public void setMenuId(String menuId) {
                this.menuId = menuId;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setRecipe(RecipeEntity recipe) {
                this.recipe = recipe;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public void setCtgIds(List<String> ctgIds) {
                this.ctgIds = ctgIds;
            }

            public String getCtgTitles() {
                return ctgTitles;
            }

            public String getMenuId() {
                return menuId;
            }

            public String getName() {
                return name;
            }

            public RecipeEntity getRecipe() {
                return recipe;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public List<String> getCtgIds() {
                return ctgIds;
            }

            public static class RecipeEntity {
                private String img;
                private String ingredients;
                private String method;
                private String sumary;
                private String title;

                public void setImg(String img) {
                    this.img = img;
                }

                public void setIngredients(String ingredients) {
                    this.ingredients = ingredients;
                }

                public void setMethod(String method) {
                    this.method = method;
                }

                public void setSumary(String sumary) {
                    this.sumary = sumary;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getImg() {
                    return img;
                }

                public String getIngredients() {
                    return ingredients;
                }

                public String getMethod() {
                    return method;
                }

                public String getSumary() {
                    return sumary;
                }

                public String getTitle() {
                    return title;
                }
            }
        }
    }
}
