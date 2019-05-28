package com.pspdfkit.cordova.action;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.view.ContextThemeWrapper;

import com.pspdfkit.configuration.activity.PdfActivityConfiguration;
import com.pspdfkit.configuration.activity.ThumbnailBarMode;
import com.pspdfkit.configuration.page.PageFitMode;
import com.pspdfkit.configuration.page.PageScrollDirection;
import com.pspdfkit.configuration.page.PageScrollMode;
import com.pspdfkit.configuration.sharing.ShareFeatures;
import com.pspdfkit.cordova.CordovaPdfActivity;
import com.pspdfkit.cordova.PSPDFKitCordovaPlugin;
import com.pspdfkit.cordova.PSPDFKitCordovaPluginException;
import com.pspdfkit.preferences.PSPDFKitPreferences;
import com.pspdfkit.ui.PdfActivity;
import com.pspdfkit.ui.PdfActivityIntentBuilder;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.EnumSet;
import java.util.Iterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GetAllUnsavedAnnotationsAction extends BasicAction {
  private static final int ARG_DOCUMENT_URI = 0;
  private static final int ARG_OPTIONS = 1;
  private static final int ARG_DOCUMENT_PASSWORD = 2;

  public GetAllUnsavedAnnotationsAction(@NonNull String name, @NonNull PSPDFKitCordovaPlugin plugin) {
    super(name, plugin);
  }

  /** Converts the given string array to an {@link EnumSet} of {@link ShareFeatures}. */
  private static EnumSet<ShareFeatures> parseShareFeatures(@Nullable JSONArray shareFeatures)
      throws JSONException {
    EnumSet<ShareFeatures> features = ShareFeatures.none();
    if (shareFeatures != null) {
      for (int i = 0; i < shareFeatures.length(); i++) {
        final ShareFeatures feature = ShareFeatures.valueOf(shareFeatures.getString(i));
        features.add(feature);
      }
    }
    return features;
  }

  /** Ensures that Javascript "null" strings are correctly converted to javas <code>null</code>. */
  @Nullable
  private static String convertJsonNullToJavaNull(@Nullable String value) {
    if (value == null || value.equals("null")) return null;
    return value;
  }

  @NonNull
  private PdfActivityConfiguration parseOptionsToConfiguration(@NonNull final JSONObject options)
      throws JSONException {
    final Activity activity = getPlugin().cordova.getActivity();
    int theme;

    try {
      ActivityInfo info =
          activity
              .getPackageManager()
              .getActivityInfo(new ComponentName(activity, PdfActivity.class), 0);
      theme = info.theme;
    } catch (PackageManager.NameNotFoundException e) {
      theme = com.pspdfkit.R.style.Theme_AppCompat_NoActionBar;
    }

    final ContextThemeWrapper themedContext = new ContextThemeWrapper(activity, theme);
    final PdfActivityConfiguration.Builder builder =
        new PdfActivityConfiguration.Builder(themedContext);
    final Iterator<String> optionIterator = options.keys();

    while (optionIterator.hasNext()) {
      final String option = optionIterator.next();
      final Object value = options.get(option);

      try {
        if ("autosaveEnabled".equals(option)) {
          builder.autosaveEnabled((Boolean) value);
        } else if ("backgroundColor".equals(option)) {
          builder.backgroundColor(Color.parseColor((String) value));
        } else if ("disableAnnotationList".equals(option) && ((Boolean) value)) {
          builder.disableAnnotationList();
        } else if ("disableAnnotationNoteHinting".equals(option)) {
          builder.setAnnotationNoteHintingEnabled(!(Boolean) value);
        } else if ("disableBookmarkEditing".equals(option) && ((Boolean) value)) {
          builder.disableBookmarkEditing();
        } else if ("disableBookmarkList".equals(option) && ((Boolean) value)) {
          builder.disableBookmarkList();
        } else if ("disableCopyPaste".equals(option)) {
          if ((Boolean) value) builder.disableCopyPaste();
          else builder.enableCopyPaste();
        } else if ("disableDocumentEditor".equals(option) && ((Boolean) value)) {
          builder.disableDocumentEditor();
        } else if ("disableOutline".equals(option) && ((Boolean) value)) {
          builder.disableOutline();
        } else if ("disablePrinting".equals(option) && ((Boolean) value)) {
          builder.disablePrinting();
        } else if ("disableSearch".equals(option) && ((Boolean) value)) {
          builder.disableSearch();
        } else if ("shareFeatures".equals(option)) {
          builder.setEnabledShareFeatures(parseShareFeatures((JSONArray) value));
        } else if ("disableUndoRedo".equals(option)) {
          builder.undoEnabled(!(Boolean) value);
        } else if ("hidePageLabels".equals(option) && ((Boolean) value)) {
          builder.hidePageLabels();
        } else if ("hidePageNumberOverlay".equals(option) && ((Boolean) value)) {
          builder.hidePageNumberOverlay();
        } else if ("hideSettingsMenu".equals(option) && ((Boolean) value)) {
          builder.hideSettingsMenu();
        } else if ("thumbnailBarMode".equals(option)) {
          builder.setThumbnailBarMode(ThumbnailBarMode.valueOf((String) value));
        } else if ("hideThumbnailGrid".equals(option) && ((Boolean) value)) {
          builder.hideThumbnailGrid();
        } else if ("memoryCacheSize".equals(option)) {
          builder.memoryCacheSize((Integer) value);
        } else if ("pageFitMode".equals(option)) {
          builder.fitMode(PageFitMode.valueOf((String) value));
        } else if ("scrollDirection".equals(option)) {
          builder.scrollDirection(PageScrollDirection.valueOf((String) value));
        } else if ("scrollMode".equals(option)) {
          builder.scrollMode(PageScrollMode.valueOf((String) value));
        } else if ("invertColors".equals(option)) {
          builder.invertColors((Boolean) value);
        } else if ("toGrayscale".equals(option)) {
          builder.toGrayscale((Boolean) value);
        } else if ("title".equals(option)) {
          builder.title(convertJsonNullToJavaNull(options.getString("title")));
        } else if ("startZoomScale".equals(option)) {
          builder.startZoomScale((float) options.getDouble("startZoomScale"));
        } else if ("maxZoomScale".equals(option)) {
          builder.maxZoomScale((float) options.getDouble("maxZoomScale"));
        } else if ("zoomOutBounce".equals(option)) {
          builder.zoomOutBounce(options.getBoolean("zoomOutBounce"));
        } else if ("page".equals(option)) {
          builder.page(options.getInt("page"));
        } else if ("useImmersiveMode".equals(option)) {
          builder.useImmersiveMode(options.getBoolean("useImmersiveMode"));
        } else if ("searchType".equals(option)) {
          final String searchType = options.getString("searchType");
          if ("SEARCH_INLINE".equals(searchType))
            builder.setSearchType(PdfActivityConfiguration.SEARCH_INLINE);
          else if ("SEARCH_MODULAR".equals(searchType))
            builder.setSearchType(PdfActivityConfiguration.SEARCH_MODULAR);
          else throw new IllegalArgumentException(String.format("Invalid search type: %s", value));
        } else if ("annotationEditing".equals(option)) {
          final JSONObject annotationEditing = options.getJSONObject("annotationEditing");
          final Iterator<String> annotationOptionIterator = annotationEditing.keys();

          while (annotationOptionIterator.hasNext()) {
            final String annotationEditingOption = annotationOptionIterator.next();
            final Object annotationEditingValue = annotationEditing.get(annotationEditingOption);

            if ("enabled".equals(annotationEditingOption)) {
              if ((Boolean) annotationEditingValue) builder.enableAnnotationEditing();
              else builder.disableAnnotationEditing();
            } else if ("creatorName".equals(annotationEditingOption)) {
              PSPDFKitPreferences.get(activity)
                  .setAnnotationCreator(
                      convertJsonNullToJavaNull(annotationEditing.getString("creatorName")));
            } else {
              throw new IllegalArgumentException(
                  String.format("Invalid annotation editing option '%s'", annotationEditingOption));
            }
          }
        } else {
          throw new IllegalArgumentException(String.format("Invalid plugin option '%s'", option));
        }
      } catch (Exception ex) {
        throw new PSPDFKitCordovaPluginException(
            String.format("Error while parsing option '%s'", option), ex);
      }
    }

    return builder.build();
  }

  @Override
  protected void execAction(JSONArray args, CallbackContext callbackContext) throws Exception {
    final PdfActivityConfiguration configuration =
        parseOptionsToConfiguration(args.getJSONObject(ARG_OPTIONS));
    final String password = convertJsonNullToJavaNull(args.getString(ARG_DOCUMENT_PASSWORD));
    final Uri documentUri = Uri.parse(args.getString(ARG_DOCUMENT_URI));
    showDocumentFromUri(documentUri, password, configuration, callbackContext);
  }

  protected void showDocumentFromUri(
      @NonNull final Uri uri,
      @Nullable final String password,
      @NonNull final PdfActivityConfiguration configuration,
      @NonNull final CallbackContext callbackContext) {
    final PSPDFKitCordovaPlugin plugin = getPlugin();
    final Intent launchIntent =
        PdfActivityIntentBuilder.fromUri(plugin.cordova.getContext(), uri)
            .activityClass(CordovaPdfActivity.class)
            .passwords(password)
            .configuration(configuration)
            .build();
    plugin.cordova.startActivityForResult(getPlugin(), launchIntent, 0);
    callbackContext.success();
  }
}
