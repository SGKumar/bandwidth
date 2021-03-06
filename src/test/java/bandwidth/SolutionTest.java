package bandwidth;

//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
import bandwidth.util.FileUtil;
import org.junit.Assert;

import org.junit.Test;

import java.io.IOException;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SolutionTest {

    private String status = "Running %s with testcase-%d";

    @Test
    public void runTests() {
        File file = null;
        try {
            String fileName = "bandwidth.csv";
            file = FileUtil.getFile(getClass().getClassLoader(), fileName);

            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            if(lines.size() == 0) {
                throw new IllegalArgumentException("Empty file! " + fileName);
            }

            // first line is number of tests
            int tests = Integer.parseInt(lines.get(0));

            // for each test 
            // number of channels/parallel shows with 3 columns
            int numLines = 1;
            System.out.println("tests " + tests);
            for(int i = 0; i < tests; i++) {
                int channels = Integer.parseInt(lines.get(numLines));
                numLines++;

                long[][] channelsInfo = new long[channels][3];
                for(int channel = 0; channel < channels; channel++) {
                    List<Long> s = Stream.of(lines.get(numLines).split(","))
                            .map(String::trim)
                            .map(Long::parseLong)
                            .collect(Collectors.toList());

                    for(int col = 0; col < 3; col++) {
                        channelsInfo[channel][col] = s.get(col);
                    }
                    numLines++;
                }

                int expected = Integer.parseInt(lines.get(numLines));
                numLines++;

                callSolution3(channelsInfo, i+1, expected);
                callSolution2(channelsInfo, i+1, expected);

            }
            //lines.forEach(System.out::println);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
        catch (URISyntaxException e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
    }

    private void callSolution2(long[][] channelsInfo, int test, int expected) {
        System.out.println(String.format(this.status, "Solution2", test));

        long bandwidth = Solution2.maxBandwidth2(channelsInfo);

        Assert.assertEquals(expected, bandwidth);
    }

    private void callSolution3(long[][] channelsInfo, int test, int expected) {
        System.out.println(String.format(this.status, "Solution3", test));
        long bandwidth = Solution3.maxBandwidth3(channelsInfo);

        Assert.assertEquals(expected, bandwidth);
    }
}