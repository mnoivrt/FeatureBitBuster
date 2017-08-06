package helper;

public class SimpleFeatureBitDetector implements FeatureBitDetector {

    public boolean isFeatureBit(String text, String fbName) {
        return text.contains(FeatureBitsConsts.FB_SERVICE_CALL + "(\"" + fbName + "\")");
    }
}
