package tk.khecnotice.khecnotice.parser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import tk.khecnotice.khecnotice.model.ClassRoutine;

public class ClassRoutineXMLParser {
    public static final String TAG = ClassRoutineXMLParser.class.toString();

    public static List<ClassRoutine> parseFeed(String content) {

        List<ClassRoutine> classRoutineList = new ArrayList<>();
        try {
            boolean inDataItemTag = false;
            String currentTagName = "";

            ClassRoutine classRoutine = null;

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {

                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        if (currentTagName.equals("period")) {
                            inDataItemTag = true;
                            classRoutine = new ClassRoutine();
                            classRoutineList.add(classRoutine);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("period")) {
                            inDataItemTag = false;
                        }
                        currentTagName = "";
                        break;
                    case XmlPullParser.TEXT:
                        if (inDataItemTag && classRoutine != null) {
                            switch (currentTagName) {
                                case "id":
                                    classRoutine.setId(Integer.parseInt(parser.getText()));
                                case "dayId":
                                    classRoutine.setDayId(parser.getText());
                                    break;
                                case "nameOfSubject":
                                    classRoutine.setNameOfSubject(parser.getText());
                                    break;
                                case "subjectTeacher":
                                    classRoutine.setSubjectTeacher(parser.getText());
                                    break;
                                case "beg1":
                                    classRoutine.setBeg1(parser.getText());
                                    break;
                                case "beg2":
                                    classRoutine.setBeg2(parser.getText());
                                    break;
                                case "end1":
                                    classRoutine.setEnd1(parser.getText());
                                    break;

                                case "end2":
                                    classRoutine.setEnd2(parser.getText());

                                    break;
                                default:
                                    break;

                            }
                        }
                        break;
                    default:
                        break;
                }

                eventType = parser.next();
            }
            Log.d(TAG, "out of xml parser");


            return classRoutineList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
