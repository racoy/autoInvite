import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class AccFilter {
    public static void dropDuplicate(StringBuilder stringBuilder) {
        HashSet<String> accs = new HashSet<>(Arrays.asList(stringBuilder.toString().split("\n")));
        stringBuilder.delete(0, stringBuilder.length());
        for (String s : accs) {
            stringBuilder.append(s);
            stringBuilder.append('\n');
        }
    }

    public static StringBuilder clearList(ArrayList<String> accsOld, ArrayList<String> accsNew, int rowsChecked) {
        int beginingIndex = 0;
        int newBegin = 0;
        StringBuilder inviteList = new StringBuilder();
        boolean beginFounded = false;
        int j = 0;
        while (!beginFounded) {
            for (int k = 0; k < accsOld.size(); k++) {
                if (accsNew.get(j).equals(accsOld.get(k))) {
                    beginingIndex = k;
                    break;
                }
            }
            for (int k = 1; k <= rowsChecked; k++) {
                if (((j + k) >= accsNew.size()) || (beginingIndex + k) >= accsOld.size()) break;
                if (!accsNew.get(j + k).equals(accsOld.get(beginingIndex + k))) {
                    break;
                }
                if (k == rowsChecked) {
                    newBegin = j;
                    beginFounded = true;
                    break;
                }
            }
            j++;
            if (j == accsNew.size() - 2) break;
        }
        for (int k = newBegin; k < accsNew.size(); k++) {
            inviteList.append(accsNew.get(k));
            inviteList.append('\n');
        }
        return inviteList;
    }
}
