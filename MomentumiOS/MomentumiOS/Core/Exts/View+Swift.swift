//
//  View+Swift.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/1/22.
//  Copyright © 2022 Mwai Banda. All rights reserved.
//

import SwiftUI
import Introspect

extension View {
    var screenBounds: CGRect {
        return UIScreen.main.bounds
    }
}

extension UIDevice {
    static var isIPad: Bool {
        UIDevice.current.userInterfaceIdiom == .pad
    }
    
    static var isIPhone: Bool {
        UIDevice.current.userInterfaceIdiom == .phone
    }
}

extension View {
    func placeholder<Content: View>(
        when shouldShow: Bool,
        alignment: Alignment = .leading,
        @ViewBuilder placeholder: () -> Content) -> some View {

        ZStack(alignment: alignment) {
            placeholder().opacity(shouldShow ? 1 : 0)
            self
        }
    }
}

extension View {
    func cornerRadius(_ radius: CGFloat, corners: UIRectCorner) -> some View {
        clipShape( RoundedCorner(radius: radius, corners: corners) )
    }
}


extension View {
    public func textFieldFocusableArea() -> some View {
        TextFieldButton { self.contentShape(Rectangle()) }
    }
}

fileprivate struct TextFieldButton<Label: View>: View {
    init(label: @escaping () -> Label) {
        self.label = label
    }
    
    var label: () -> Label
    
    private var textField = Weak<UITextField>(nil)
    
    var body: some View {
        Button(action: {
            self.textField.value?.becomeFirstResponder()
        }, label: {
            label().introspectTextField {
                self.textField.value = $0
            }
        }).buttonStyle(PlainButtonStyle())
    }
}

/// Holds a weak reference to a value
public class Weak<T: AnyObject> {
    public weak var value: T?
    public init(_ value: T?) {
        self.value = value
    }
}

