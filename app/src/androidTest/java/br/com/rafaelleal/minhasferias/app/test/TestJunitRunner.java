package br.com.rafaelleal.minhasferias.app.test;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import java.io.File;
import br.com.rafaelleal.minhasferias.app.application.MyApplication;
import dagger.hilt.android.testing.HiltTestApplication;
import io.cucumber.android.runner.CucumberAndroidJUnitRunner;
import io.cucumber.junit.CucumberOptions;


@CucumberOptions(
        features = "features",
        glue = "br.com.rafaelleal.minhasferias.app.test"
)
public class TestJunitRunner extends CucumberAndroidJUnitRunner {

    @Override
    public void onCreate(final Bundle bundle) {
        bundle.putString("plugin", getPluginConfigurationString());
        new File(getAbsoluteFilesPath()).mkdirs();
        super.onCreate(bundle);
    }

    private String getPluginConfigurationString() {
        String cucumber = "cucumber";
        String separator = "--";
        return "junit:" + getCucumberXml(cucumber) + separator +
                "html:" + getCucumberHtml(cucumber);
    }

    private String getCucumberHtml(String cucumber) {
        return getAbsoluteFilesPath() + "/" + cucumber + ".html";
    }

    private String getCucumberXml(String cucumber) {
        return getAbsoluteFilesPath() + "/" + cucumber + ".xml";
    }

    private String getAbsoluteFilesPath() {
        //sdcard/Android/data/<dir>
        File directory = getTargetContext().getExternalFilesDir(null);
        return new File(directory, "reports").getAbsolutePath();
    }

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return super.newApplication(cl, HiltTestApplication.class.getName(), context);
    }
}
