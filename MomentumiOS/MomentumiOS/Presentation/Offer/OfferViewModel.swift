//
//  OfferViewModel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/2/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class OfferViewModel: ObservableObject {
    let offerKeypad: [[Character]] = [
        ["1", "2", "3"],
        ["4", "5", "6"],
        ["7", "8", "9"],
        ["•", "0", "<"]
    ]
    let controlKeys: [Character] = ["•", "<"]
    @Published var isDecimalMode = false
    @Published var isKeypadDisabled = false
    @Published var scale = 1.3
    @Published var displayText = [String]()

    @Published private var number = "" {
        willSet {
            if isDecimalMode {
                displayNumber = "$" + getDecimalFormattedNumber(number: newValue)
            } else {
                displayNumber = "$" + getFormattedNumber(number: newValue)
            }
        }
    }
    
    @Published var displayNumber = "" {
        willSet {
            displayText = Array(arrayLiteral: newValue)
        }
    }

  
    
    func processInput(button: Character) {
        if button.isNumber {
            processNumber(button: button)
        } else if button.isPunctuation {
            processPunctuation(button: button)
        } else if button.isSymbol {
            processBackspace(button: button)
        }
        processScale()
    }
    
    private func processNumber(button: Character) {
        if number.first == "0" {
            number = String(button)
        } else {
            if isDecimalMode {
                processDecimalModeNumber(button: button)
            } else {
                number += String(button)
            }
        }
        processScale()
    }
    
    private func processScale() {
        if displayNumber.count < 5 {
            if scale != 1.3 {
                scale = 1.3
            }
            scale -= 0.1
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.15) {
                self.scale += 0.1
            }
            
        } else {
            if scale == 1.3 {
                scale -= 0.5
            } else {
                scale -= 0.1
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.15) {
                    self.scale += 0.1
                }
            }
        }
        
    }
    private func getFormattedNumber(number: String) -> String {
        switch number.count {
        case 4 :
            isKeypadDisabled = false
            return "\(number.dropLast(3)),\(number.dropFirst())"
        case 5:
            isKeypadDisabled = true
            return "\(number.dropLast(3)),\(number.dropFirst(2))"
        default:
            isKeypadDisabled = false
            return number
        }
    }
    private func getDecimalFormattedNumber(number: String) -> String {
        switch number.dropLast(3).count {
        case 4 :
            return "\(number.dropLast(6)),\(number.dropFirst())"
        case 5:
            return "\(number.dropLast(6)),\(number.dropFirst(2))"
        default:
            return number
        }
    }
    
    
    private func processPunctuation(button: Character) {
        if number.dropLast(2).last == "." {
            isDecimalMode = false
            number = String(number.dropLast(3))
        } else {
            isDecimalMode = true
            isKeypadDisabled = false
            number += ".00"
            Log.d(tag: "isDecimal", message: "\(isDecimalMode)")
        }
    }
    
    private func processBackspace(button: Character) {
        if number.count == 1 {
            number = "0"
        } else {
            if isDecimalMode {
                processDecimalModeBackspace(button: button)
            } else {
                number = String(number.dropLast())
            }
        }
    }
    
    private func processDecimalModeBackspace(button: Character) {
        if  String(number.dropLast().last ?? Character("")) + String(number.last ?? Character("")) == "00" {
            isDecimalMode = false
            number = String(number.dropLast(3))
        } else {
            if number.last == "0" {
                number = String(number.dropLast(2)) + "00"
            } else {
                number = String(number.dropLast()) + "0"
            }
        }
    }
    
    private func processDecimalModeNumber(button: Character) {
        if number.dropLast().last == "0" {
            number = number.dropLast(2) + String(button) + "0"
        } else {
            if number.last == "0" {
                number = number.dropLast() + String(button)
            }
        }
    }
    
}


