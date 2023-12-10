package sk.comma.business;

import sk.comma.entity.Kategoria;
import sk.comma.entity.Sutaz;

import java.util.List;

public interface OverviewManager {
    List<vysledkyOverview> getOverviews(Kategoria kategoria, Sutaz sutaz);
}
