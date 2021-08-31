import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class FilterTests {
    @Test
    public void testClearList() {
        ArrayList<String> accsOld = new ArrayList<String>(
                Arrays.asList("TamaraKaznacheeva",
                        "FreeDobby50",
                        "Faxriddinkadyrov",
                        "viforgotten",
                        "free_lana4ka",
                        "Frozenhauer",
                        "fedosplus",
                        "anna_feyasmm",
                        "faqall",
                        "alekseenkofamily",
                        "fedsve",
                        "k_frem",
                        "PavelFrolov_ROBBO",
                        "flamursveta",
                        "fomichheva",
                        "Little_pony51",
                        "Rocksy_fox",
                        "elis_fm",
                        "fadeevavalenka",
                        "pochemuizashto",
                        "little_fox13",
                        "anzh_fokina",
                        "froliccc",
                        "ais_cream",
                        "Father_Pontiy",
                        "frozinov13",
                        "Enotek_fr",
                        "itsnotkatyaa",
                        "red_flower13",
                        "freckledann",
                        "Fomina03",
                        "Lyudmila_Fed",
                        "freelancer_AnnaKabaeva",
                        "Andrew_Finger",
                        "danil_fedosov",
                        "maryferber",
                        "nk_fly",
                        "fritur356",
                        "Forelb",
                        "statyetka",
                        "FaridaSo8",
                        "pletu_kosy_ekb",
                        "angela_fof",
                        "ksu_fine",
                        "lena_faz")
        );
        ArrayList<String> accsNew = new ArrayList<String>(
                Arrays.asList("TamaraKaznacheeva",
                        "FreeDobby50",
                        "viforgotten",
                        "free_lana4ka",
                        "Frozenhauer",
                        "fedosplus",
                        "anna_feyasmm",
                        "faqall",
                        "alekseenkofamily",
                        "fedsve",
                        "k_frem",
                        "flamursveta",
                        "fomichheva",
                        "Little_pony51",
                        "Rocksy_fox",
                        "anna_feyasmm",
                        "fadeevavalenka",
                        "pochemuizashto",
                        "little_fox13",
                        "anzh_fokina",
                        "froliccc",
                        "ais_cream",
                        "Father_Pontiy",
                        "frozinov13",
                        "Enotek_fr",
                        "itsnotkatyaa",
                        "red_flower13",
                        "freckledann",
                        "Fomina03",
                        "Lyudmila_Fed",
                        "freelancer_AnnaKabaeva",
                        "Andrew_Finger",
                        "danil_fedosov",
                        "maryferber",
                        "nk_fly",
                        "fritur356",
                        "Forelb",
                        "statyetka",
                        "FaridaSo8",
                        "pletu_kosy_ekb",
                        "angela_fof",
                        "ksu_fine",
                        "lena_faz")
        );

        StringBuilder expResult = new StringBuilder("fadeevavalenka\n" +
                "pochemuizashto\n" +
                "little_fox13\n" +
                "anzh_fokina\n" +
                "froliccc\n" +
                "ais_cream\n" +
                "Father_Pontiy\n" +
                "frozinov13\n" +
                "Enotek_fr\n" +
                "itsnotkatyaa\n" +
                "red_flower13\n" +
                "freckledann\n" +
                "Fomina03\n" +
                "Lyudmila_Fed\n" +
                "freelancer_AnnaKabaeva\n" +
                "Andrew_Finger\n" +
                "danil_fedosov\n" +
                "maryferber\n" +
                "nk_fly\n" +
                "fritur356\n" +
                "Forelb\n" +
                "statyetka\n" +
                "FaridaSo8\n" +
                "pletu_kosy_ekb\n" +
                "angela_fof\n" +
                "ksu_fine\n" +
                "lena_faz\n");

        Assert.assertEquals(expResult.toString(), AccFilter.clearList(accsOld, accsNew, 10).toString());
    }

    @Test
    public void testDropDuplicated(){
        StringBuilder rows = new StringBuilder("fadeevavalenka\n" +
                "anzh_fokina\n" +
                "pochemuizashto\n" +
                "Father_Pontiy\n" +
                "Father_Pontiy\n" +
                "Father_Pontiy\n" +
                "frozinov13\n" +
                "little_fox13\n" +
                "anzh_fokina\n" +
                "froliccc\n" +
                "ais_cream\n" +
                "Father_Pontiy\n" +
                "frozinov13\n");

        StringBuilder expResult = new StringBuilder("fadeevavalenka\n" +
                "pochemuizashto\n" +
                "little_fox13\n" +
                "anzh_fokina\n" +
                "froliccc\n" +
                "ais_cream\n" +
                "Father_Pontiy\n" +
                "frozinov13\n");

        AccFilter.dropDuplicate(rows);
        String[] rowsMas = rows.toString().split("\n");
        Arrays.sort(rowsMas);
        String[] expMas = expResult.toString().split("\n");
        Arrays.sort(expMas);
        Assert.assertArrayEquals(expMas, rowsMas);

    }
}
