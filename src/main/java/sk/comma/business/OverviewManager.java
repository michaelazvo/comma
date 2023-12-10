package sk.comma.business;

import sk.comma.entity.Kategoria;

import java.util.List;

public interface OverviewManager {
    List<vysledkyOverview> getOverviews(Kategoria kategoria);
}
