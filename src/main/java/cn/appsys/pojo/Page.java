package cn.appsys.pojo;



import cn.appsys.tools.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Page {

    @Getter
    private Integer totalCount;

    @Getter
    private Integer currentPageNo;

    @Getter
    private Integer totalPageCount;

    public Page(Integer totalCount, Integer pageNo){
        this.currentPageNo = pageNo;
        this.totalCount = totalCount;
        this.totalPageCount = totalCount / Constants.pageSize;
        if (totalCount % Constants.pageSize > 0){
            this.totalPageCount++;
        }
    }
}
