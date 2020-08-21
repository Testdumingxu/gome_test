package com.gome.oa.common;

import com.gome.oa.pojo.Project;
import com.gome.oa.pojo.Suite;
import com.gome.oa.pojo.TestReport;
import lombok.Data;

import java.util.List;

@Data
public class SuiteListVO extends Suite {

    // 关联项目
    private List<Project> Project;

}
