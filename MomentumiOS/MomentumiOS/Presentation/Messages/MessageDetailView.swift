//
//  MessageDetailView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 11/7/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import SwiftUI
import UIKit
import MomentumSDK
import SDWebImageSwiftUI
import Algorithms

enum NoteState {
    case Loading
    case Display
}
struct MessageDetailView: View {
    let message: Message
    @ObservedObject var messageViewModel: MessageViewModel
    @EnvironmentObject var session: Session
    @State private var notes = ""
    @State private var showNotes = false
    @State private var showAlert = false
    @State private var isUpdating = false
    @State private var showAuthSheet = false
    @State private var currentNote: Note? = nil
    @State private var currentPassage: Passage? = nil
    @State private var passages = [Passage]()
    @State private var currentNoteState: NoteState = .Display
    
    var body: some View {
        ZStack {
            ScrollView {
                VStack(alignment: .leading, spacing: 0) {
                    WebImage(url: URL(string: message.thumbnail))
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(maxHeight: 350)
                        .edgesIgnoringSafeArea(.top)
                    //                TextView(text: $text, range: $range)
                    //                    .frame(maxWidth: .infinity, minHeight: 100)
                    
                    ForEach(passages) { passage in
                        if (passage.header ?? "").isEmpty {
                            VStack(alignment: .leading) {
                                HStack {
                                    Text(passage.verse ?? "")
                                        .fontWeight(.heavy)
                                    Spacer()
                                }
                                if let message = passage.message  {
                                    if #available(iOS 15, *) {
                                        Text(getMesssageAttritubuted(message))
                                            .textSelection(.enabled)
                                    } else {
                                        Group {
                                            getMessageTextArray(message: message).prefix(450).reduce(Text("")) { x, y in
                                                x + y
                                            }
                                        }
                                    }
                                }
                            }.frame(maxWidth: .infinity).padding(10)
                        } else {
                            HStack {
                                Text(passage.header ?? "")
                                Spacer()
                            }.padding(10)
                        }
                        if let passageNotes = passage.notes {
                            
                            Text("NOTES")
                                .fontWeight(.heavy)
                                .padding(.top, 10)
                                .padding(.horizontal, 10)
                            ForEach(passageNotes, id: \.id) { note in
                                Menu {
                                    Button {
                                        showNotes = true
                                        notes = note.content
                                        isUpdating = true
                                        currentNote = note
                                        currentPassage = passage
                                    } label: {
                                        Label("Update", systemImage: "square.and.pencil")
                                    }
                                    Button {
                                        currentNote = note
                                        currentPassage = passage
                                        showAlert = true
                                    } label: {
                                        Label("Delete", systemImage: "trash")
                                    }
                                    
                                } label: {
                                    Text(note.content)
                                        .multilineTextAlignment(.leading)
                                        .foregroundColor(Color.black)
                                        .padding(.horizontal, 10)
                                        .padding(.bottom, 10)
                                }
                            }
                            Text("ADD MORE NOTES")
                                .fontWeight(.heavy)
                                .padding(10)
                                .onTapGesture {
                                    showNotes = true
                                    notes = ""
                                    isUpdating = false
                                    currentPassage = passage
                                }
                            
                        } else {
                            Text("ADD NOTES")
                                .fontWeight(.heavy)
                                .padding(10)
                                .onTapGesture {
                                    if (session.currentUser?.isGuest ?? false)  {
                                        showAuthSheet.toggle()
                                    } else {
                                        showNotes = true
                                        notes = ""
                                        isUpdating = false
                                        currentPassage = passage
                                    }
                                }
                        }
                        
                        
                        Divider()
                        
                    }
                    
                    
                }
            }
            .alert(isPresented: $showAlert, content: {
                Alert(
                    title: Text("Delete Note"),
                    message: Text("Are you sure you want to delete this note?"),
                    primaryButton: .default(Text("Cancel"), action: {
                        showAlert = false
                    }),
                    secondaryButton: .destructive(Text("Delete"), action: {
                        messageViewModel.deleteNote(noteId: currentNote?.id ?? "", onCompletion: {
                            passages = passages.map({
                                if $0.id == currentPassage?.id {
                                    return Passage(
                                        id: $0.id,
                                        header: $0.header,
                                        verse: $0.verse,
                                        message: $0.message,
                                        notes: $0.notes?.filter({
                                            $0.id != currentNote?.id
                                        }))
                                } else {
                                    return $0
                                }
                            })
                            showAlert = false
                            messageViewModel.clearMessagesCache()
                        })
                    })
                )
            })
            .navigationBarTitleDisplayMode(.inline)
            .onAppear {
                passages = messageViewModel.passages
            }
            .sheet(isPresented: $showAuthSheet) {
                MomentumBlurredBackground {
                    AuthControllerView()
                }
            }
            if showNotes {
                Color.black.opacity(0.5).edgesIgnoringSafeArea(.all)
                VStack {
                    Spacer()
                    VStack(alignment: .leading) {
                        Text("Enter your notes")
                            .bold()
                        switch(currentNoteState) {
                        case .Display:
                            TextEditor(text: $notes)
                                .frame(height: 150)
                                .overlay(
                                    RoundedRectangle(cornerRadius: 10)
                                        .stroke(.gray, lineWidth: 1)
                                )
                        case .Loading:
                            VStack(alignment: .center) {
                                Spacer()
                                HStack {
                                    Spacer()
                                    ProgressView()
                                        .progressViewStyle(CircularProgressViewStyle())
                                    Spacer()
                                }
                                Spacer()
                            }.frame(maxWidth: .infinity, minHeight: 151, maxHeight: 151)
                        }
                        
                        HStack {
                            Button {
                                showNotes = false
                                isUpdating = false
                                notes = ""
                            } label: {
                                HStack {
                                    Spacer()
                                    Text("Cancel")
                                        .padding(.vertical, 8)
                                    Spacer()
                                }
                            }.buttonStyle(FilledButtonStyle())
                            Spacer()
                            Button {
                                currentNoteState = .Loading
                                if isUpdating {
                                    messageViewModel.updateNote(note: Note.UserNote(
                                        id: currentNote?.id ?? "" ,
                                        content: notes,
                                        userId: session.currentUser?.id ?? ""
                                    )) {
                                        passages = passages.map({
                                            if $0.id == currentPassage?.id {
                                                return Passage(
                                                    id: $0.id,
                                                    header: $0.header,
                                                    verse: $0.verse,
                                                    message: $0.message,
                                                    notes: $0.notes?.map({
                                                        if $0.id == currentNote?.id {
                                                            Note(id: currentNote?.id ?? "", content: notes)
                                                        } else {
                                                            $0
                                                        }
                                                    }))
                                            } else {
                                                return $0
                                            }
                                        })
                                        showNotes = false
                                        isUpdating = false
                                        notes = ""
                                        messageViewModel.clearMessagesCache()
                                        DispatchQueue.main.asyncAfter(deadline: .now() + 1, execute: {
                                            currentNoteState = .Display
                                        })
                                    }
                                } else {
                                    messageViewModel.addNote(note: NoteRequest(content: notes, userId: session.currentUser?.id ?? "", passageId: currentPassage?.id ?? "")) {
                                        passages = passages.map({
                                            if $0.id == currentPassage?.id {
                                                return Passage(
                                                    id: $0.id,
                                                    header: $0.header,
                                                    verse: $0.verse,
                                                    message: $0.message,
                                                    notes: ($0.notes ?? []) + [
                                                        Note(id: UUID().uuidString, content: notes)
                                                    ])
                                            } else {
                                                return $0
                                            }
                                        })
                                        showNotes = false
                                        isUpdating = false
                                        notes = ""
                                        messageViewModel.clearMessagesCache()
                                        DispatchQueue.main.asyncAfter(deadline: .now() + 1, execute: {
                                            currentNoteState = .Display
                                        })
                                    }
                                }
                            } label: {
                                HStack {
                                    Spacer()
                                    Text("Save")
                                        .padding(.vertical, 8)
                                    Spacer()
                                }
                            }.buttonStyle(FilledButtonStyle())
                        }
                    }.padding(20).frame(width: screenBounds.width * 0.8).background(Color.white).clipShape(RoundedCorner(radius: 10))
                    Spacer()
                }
            }
        }
    }
    
    func getMessageTextArray(message: String)  -> [Text] {
        message.map({
            if Character(extendedGraphemeClusterLiteral: $0).isNumber {
                Text(String($0))
                    .foregroundColor(.gray)
                    .bold()
                    .font(.caption2)
            } else {
                Text(String($0))
            }
        })
    }
    
    @available(iOS 15, *)
    func getMesssageAttritubuted(_ s: String) -> AttributedString {
        s.map({
            if Character(extendedGraphemeClusterLiteral: $0).isNumber {
                var res = AttributedString(String($0))
                res.font = .caption.bold()
                res.foregroundColor = .gray
                return res
            } else {
                return AttributedString(String($0))
            }
        }).reduce(AttributedString(""), { x, y in x + y })
    }
}


struct TextView: UIViewRepresentable {
    @Binding var text: String
    @Binding var range: NSRange
    
    func makeCoordinator() -> Coordinator {
        Coordinator(self, range: $range)
    }
    
    func makeUIView(context: Context) -> UITextView {
        
        let myTextView = UITextView()
        myTextView.delegate = context.coordinator
        
        myTextView.font = UIFont(name: "HelveticaNeue", size: 15)
        myTextView.isScrollEnabled = true
        myTextView.isEditable = false
        myTextView.isUserInteractionEnabled = true
        myTextView.backgroundColor = UIColor(white: 0.0, alpha: 0.05)
        
        return myTextView
    }
    
    func updateUIView(_ uiView: UITextView, context: Context) {
        uiView.text = text
    }
    
    class Coordinator : NSObject, UITextViewDelegate {
        
        var parent: TextView
        @Binding var range: NSRange // <---
        
        init(_ uiTextView: TextView, range: Binding<NSRange>) {
            self.parent = uiTextView
            _range = range
        }
        
        func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
            return true
        }
        
        func textViewDidChange(_ textView: UITextView) {
            print("text now: \(String(describing: textView.text!))")
            self.parent.text = textView.text
        }
        func textViewDidChangeSelection(_ textView: UITextView) {
            range = textView.selectedRange
        }
    }
}



