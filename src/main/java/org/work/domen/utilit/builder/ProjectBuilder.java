package org.work.domen.utilit.builder;

import org.work.domen.entity.Project;
import org.work.domen.entity.ProjectMarkStatus;
import org.work.domen.entity.Segment;

public interface ProjectBuilder {

    ProjectBuilder withSegment(Segment segment);

    ProjectBuilder withName(String name);

    ProjectBuilder withHead(String head);

    ProjectBuilder withHumanAmount(double humanAmount);

    ProjectBuilder withAverageSalary(double averageSalary);

    ProjectBuilder withProfit(double profit);

    ProjectBuilder withTerm(double term);

    ProjectBuilder withProfitability(double profitability);

    ProjectBuilder withProjectStatus(ProjectMarkStatus status);

    Project build();


}
