package com.dmmeta.choloja.presentation.screen.component

import androidx.compose.runtime.Composable
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDate
import platform.Foundation.setValue
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleCancel
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIApplication
import platform.UIKit.UIButton
import platform.UIKit.UIButtonTypeSystem
import platform.UIKit.UIControlStateNormal
import platform.UIKit.UIDatePicker
import platform.UIKit.UIDatePickerMode
import platform.UIKit.UILayoutConstraintAxisVertical
import platform.UIKit.UIStackView
import platform.UIKit.UIStackViewDistributionFillEqually
import platform.UIKit.UIViewController


@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@Composable
actual fun DateRangePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (Long, Long) -> Unit
) {
    val startPicker = UIDatePicker().apply {
        datePickerMode = UIDatePickerMode.UIDatePickerModeDate
        minimumDate = NSDate()
    }

    val endPicker = UIDatePicker().apply {
        datePickerMode = UIDatePickerMode.UIDatePickerModeDate
        minimumDate = NSDate()
    }
    // Buttons
    val confirmButton = UIButton.buttonWithType(UIButtonTypeSystem) as UIButton
    confirmButton.setTitle("OK", forState = UIControlStateNormal)
    val cancelButton = UIButton.buttonWithType(UIButtonTypeSystem) as UIButton
    cancelButton.setTitle("Cancel", forState = UIControlStateNormal)
    val stackView = UIStackView().apply {
        axis = UILayoutConstraintAxisVertical
        spacing = 12.0
        axis = UILayoutConstraintAxisVertical
        spacing = 10.0
        distribution = UIStackViewDistributionFillEqually
        alignment = 1
        addArrangedSubview(startPicker)
        addArrangedSubview(endPicker)
    }

    val vc = UIViewController()
    vc.view.addSubview(stackView)
    stackView.translatesAutoresizingMaskIntoConstraints = false
    stackView.topAnchor.constraintEqualToAnchor(vc.view.topAnchor, 20.0).isActive()
    stackView.leadingAnchor.constraintEqualToAnchor(vc.view.leadingAnchor, 20.0).isActive()
    stackView.trailingAnchor.constraintEqualToAnchor(vc.view.trailingAnchor, -20.0).isActive()
    stackView.bottomAnchor.constraintEqualToAnchor(vc.view.bottomAnchor, -20.0).isActive()

    val rootVC = UIApplication.sharedApplication.keyWindow?.rootViewController ?: return

    val alertVC = UIAlertController()
    alertVC.setValue(vc, forKey = "contentViewController")

    alertVC.addAction(
        UIAlertAction.actionWithTitle("Cancel", UIAlertActionStyleCancel) {
            onDismiss()
        }
    )
    alertVC.addAction(
        UIAlertAction.actionWithTitle("OK", UIAlertActionStyleDefault) {
            val start = (startPicker.date.timeIntervalSince1970 * 1000).toLong()
            val end = (endPicker.date.timeIntervalSince1970 * 1000).toLong()
            onConfirm(start, end)
        }
    )

    rootVC.presentViewController(alertVC, true, null)
}



