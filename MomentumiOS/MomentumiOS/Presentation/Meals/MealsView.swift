//
//  Meals.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 8/6/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import SwiftUI
import MultiDatePicker

struct Meal  {
    let receipient: String
    let receipientEmail: String
}

struct MealsView: View {
    @State private var showAddMealSheet = false
    var body: some View {
        Text(/*@START_MENU_TOKEN@*/"Hello, World!"/*@END_MENU_TOKEN@*/)
            .navigationBarTitleDisplayMode(.inline)
            .toolbar(
                content: {
                    ToolbarItemGroup(placement: .navigationBarLeading) {
                        Text("Meals")
                            .font(.largeTitle)
                            .bold()
                    }
                    
                    ToolbarItemGroup(placement: .navigationBarTrailing) {
                        Button {
                            withAnimation(.easeInOut(duration: 0.35)) {
                                showAddMealSheet.toggle()
                            }
                        } label: {
                            Image(systemName: "plus")
                        }
                        
                    }
                }
            )
            .sheet(isPresented: $showAddMealSheet) {
                UploadMealCardView(onDismiss: {
                    showAddMealSheet.toggle()
                })
            }
    }
}

struct Meals_Previews: PreviewProvider {
    static var previews: some View {
        MealsView()
    }
}

struct UploadMealCardView: View {
    @State private var currentPage = Int()
    @State private var timer = Timer.publish(every: 3, on: .main, in: .common).autoconnect()
    @State private var loadingState: Double = 0
    @State private var startLoading = false
    @State private var loadingIncrement: Double = 1
    @State private var description = "\u{2022} "
    @State var dates = [Date]()
    
    var onDismiss: () -> Void
    
    var body: some View {
        VStack {
            Spacer()
            ZStack {
                RoundedRectangle(cornerRadius: 10, style: .continuous)
                    .fill(Color.white)
                    .shadow(color: Color.black.opacity(0.15), radius: 3)
                
                VStack {
                    HStack {
                        VStack(alignment: .leading) {
                            Text("Let's Get Started")
                                .bold()
                                .font(.title2)
                                .foregroundColor(.gray)
                            Text("With this is new meal" )
                                .font(.headline)
                                .foregroundColor(.gray)
                                .bold()
                        }
                        Spacer()
                        HStack {
                            Text("\(currentPage + 1)").font(.caption) +
                            Text("/").font(.headline).fontWeight(.light) +
                            Text("5").font(.caption)
                        }.padding()
                            .foregroundColor(.gray)
                        
                    }.padding(.vertical)
                        .padding(.leading)
                        .padding(.top)
                    
                    if currentPage == 3 && startLoading {
                        ProgressView(value: loadingState, total: 101)
                            .progressViewStyle(LinearProgressViewStyle())
                            .accentColor(Color.black)
                        
                    } else {
                        Divider()
                    }
                    
                    TabView (selection: $currentPage) {
                        VStack {
                            Group {
                                DefaultTextfield(title: "Recipient", icon: "person", text: .constant(""))
                                Divider()
                                DefaultTextfield(title: "Email", icon: "envelope", text: .constant(""))
                                Divider()
                                DefaultTextfield(title: "Phone", icon: "envelope", text: .constant(""))
                                Divider()
                            }
                            Group {
                                DefaultTextfield(title: "City", icon: "envelope", text: .constant(""))
                                Divider()
                                DefaultTextfield(title: "Street", icon: "envelope", text: .constant(""))
                                
                            }
                            Spacer()
                        }
                        .tag(0)
                        VStack {
                            MultiDatePicker(anyDays: $dates)
                                .accentColor(.black)
                            Spacer()
                        }
                        .tag(1)
                        ScrollView(showsIndicators: false) {
                            VStack {
                                DefaultTextfield(title: "Number of Adults", icon: "person", text: .constant(""))
                                Divider()
                                DefaultTextfield(title: "Number of Kids", icon: "person", text: .constant(""))
                                Divider()
                                DefaultTextfield(title: "Preferred Delivery Time(4pm - 5pm)", icon: "envelope", text: .constant(""))
                                Divider()
                                VStack(alignment: .leading) {
                                    Text("Favorite Meals or Resturants")
                                        .foregroundColor(.gray)
                                        .font(.headline)
                                        .fontWeight(.semibold)
                                    TextEditor(text: $description)
                                        .frame(minHeight: 130, maxHeight: 130)
                                        .foregroundColor(.black)
                                        .onChange(of: description) { [description] newText in
                                            if newText.suffix(1) == "\n" && newText > description {
                                                self.description.append("\u{2022} ")
                                            }
                                        }
                                }.padding(.horizontal, 15)
                                
                                Spacer()
                            }
                        }
                        .tag(2)
                        ScrollView(showsIndicators: false)  {
                            
                            VStack(alignment: .leading) {
                                Text("Least Favorite Meals")
                                    .foregroundColor(.gray)
                                    .font(.headline)
                                    .fontWeight(.semibold)
                                TextEditor(text: $description)
                                    .frame(minHeight: 120, maxHeight: 120)
                                    .foregroundColor(.black)
                                    .onChange(of: description) { [description] newText in
                                        if newText.suffix(1) == "\n" && newText > description {
                                            self.description.append("\u{2022} ")
                                        }
                                    }
                            }.padding(.horizontal, 15)
                            Divider()
                            VStack(alignment: .leading) {
                                Text("Allergies or Dietary Restrictions")
                                    .foregroundColor(.gray)
                                    .font(.headline)
                                    .fontWeight(.semibold)
                                TextEditor(text: $description)
                                    .frame(minHeight: 120, maxHeight: 120)
                                    .foregroundColor(.black)
                                    .onChange(of: description) { [description] newText in
                                        if newText.suffix(1) == "\n" && newText > description {
                                            self.description.append("\u{2022} ")
                                        }
                                    }
                            }.padding(.horizontal, 15)
                            Divider()
                            VStack(alignment: .leading) {
                                Text("Additional Special Instructions")
                                    .foregroundColor(.gray)
                                    .font(.headline)
                                    .fontWeight(.semibold)
                                TextEditor(text: $description)
                                    .frame(minHeight: 120, maxHeight: 120)
                                    .foregroundColor(.black)
                                    .onChange(of: description) { [description] newText in
                                        if newText.suffix(1) == "\n" && newText > description {
                                            self.description.append("\u{2022} ")
                                        }
                                    }
                            }.padding(.horizontal, 15)
                        }
                        .tag(3)
                        VStack {
                            LottieView(name: "postSuccess", loopMode: .loop, alignment: .scaleAspectFit)
                                .frame(width: 200, height: 200, alignment: .center)
                            
                            Text("""
                               Your Meal has been posted successfully.
                               For more details. Check the Meals
                               section to see or edit previous meals,
                               in the Meals tab.
                               """)
                            .lineLimit(nil)
                            .fixedSize(horizontal: false, vertical: true)
                            .frame(maxHeight: .infinity)
                            .multilineTextAlignment(.center)
                            .padding()
                            .padding(.bottom)
                            Spacer()
                        }
                        .tag(4)
                    }.tabViewStyle(.page(indexDisplayMode: .never))
                    
                    Divider()
                    HStack {
                        Spacer()
                        Button { withAnimation(.easeInOut) {
                            if currentPage < 4 {
                                if currentPage == 3  {
                                    currentPage = 3
                                    startLoading = true
                                    startTimer()
                                } else {
                                    currentPage += 1
                                }
                            } else {
                                onDismiss()
                            }
                            
                        }} label: {
                            Text(currentPage < 3 ? "Next" : currentPage < 4 ? "Save" : "Dismiss")
                                .foregroundColor(.white)
                                .bold()
                                .padding()
                                .frame(maxWidth: .infinity)
                                .background(Color.black)
                                .cornerRadius(10)
                                .padding()
                        }
                        Spacer()
                        
                    }
                }
            }
            .frame(maxWidth: screenBounds.width - 65, maxHeight: 560, alignment: .center)
            .onReceive(timer) { _ in
                if startLoading && loadingState < 101 {
                    loadingState += loadingIncrement
                } else if loadingState > 99 {
                    currentPage += 1
                    stopTimer()
                }
            }
            
            Spacer()
        }
    }
    func stopTimer() {
        self.timer.upstream.connect().cancel()
    }
    
    func startTimer() {
        self.timer = Timer.publish(every: 0.01, on: .main, in: .common).autoconnect()
    }
}


struct DefaultTextfield: View {
    var title: String
    var icon: String
    @Binding var text: String
    var body: some View {
        TextField(title, text: $text)
            .textContentType(.username)
            .disableAutocorrection(true)
            .keyboardType(.emailAddress)
            .autocapitalization(.none)
            .padding(.leading, 24)
            .padding(15)
            .foregroundColor(Color.black)
            .cornerRadius(7)
            .disableAutocorrection(false)
            .overlay(
                HStack{
                    Image(systemName: icon)
                    Spacer()
                }
                    .padding(.horizontal, 12)
                    .foregroundColor(Color.gray)
                
            )
            .ignoresSafeArea(.keyboard, edges: .bottom)
    }
}
