package exercise;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    @Test
    void testTake() {
        List<Integer> list1 = new ArrayList<>();
        int count1 = 0;
        List<Integer> result1 = App.take(list1, count1);
        assertThat(result1).isEmpty();

        List<Integer> list2 = new ArrayList<>();
        int count2 = -1;
        List<Integer> result2 = App.take(list2, count2);
        assertThat(result2).isEmpty();

        List<Integer> list3 = new ArrayList<>();
        int count3 = 1;
        List<Integer> result3 = App.take(list3, count3);
        assertThat(result3).isEmpty();

        List<Integer> list4 = new ArrayList<>(Arrays.asList(1, 2, 3));
        int count4 = 4;
        List<Integer> result4 = App.take(list4, count4);
        assertThat(result4).isEqualTo(list4).last().isNotEqualTo(100);

        List<Integer> list5 = new ArrayList<>(Arrays.asList(1, 2, 3));
        int count5 = 3;
        List<Integer> result5 = App.take(list5, count5);
        assertThat(result5).isEqualTo(list5).last().isNotEqualTo(10);

        List<Integer> list6 = new ArrayList<>(Arrays.asList(1, 2, 3));
        int count6 = 2;
        List<Integer> exceptedLIst = new ArrayList<>(Arrays.asList(1, 2));
        List<Integer> result6 = App.take(list6, count6);
        assertThat(result6).isEqualTo(exceptedLIst).last().isNotEqualTo(10);

        List<Integer> list7 = new ArrayList<>(Arrays.asList(1, 2, 3));
        int count7 = -1;
        List<Integer> result7 = App.take(list7, count7);
        assertThat(result7).isEmpty();

        List<Integer> list8 = new ArrayList<>(Arrays.asList(1, 2, 3));
        int count8 = 0;
        List<Integer> result8 = App.take(list8, count8);
        assertThat(result8).isEmpty();
    }
}
