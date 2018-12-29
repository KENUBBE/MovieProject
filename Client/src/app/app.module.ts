import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import "angular2-navigate-with-data";
import { AppComponent } from './app.component';
import { LoginComponent } from './component/login/login.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { RouterModule, Routes } from '@angular/router';
import { HttpModule } from '@angular/http';
import { MatAutocompleteModule, MatFormFieldModule, MatInputModule } from '@angular/material'
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MoviepageComponent } from './component/moviepage/moviepage.component';
import { OwlDateTimeModule, OwlNativeDateTimeModule, OwlDateTimeIntl } from 'ng-pick-datetime';
import { GuardService } from './provider/guard/guard.service';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [GuardService] },
  { path: 'movie/:id', component: MoviepageComponent, canActivate: [GuardService] }
];

export class DefaultIntl extends OwlDateTimeIntl {
  cancelBtnLabel = 'Cancel';
  setBtnLabel = 'Book';
};

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    MoviepageComponent
  ],
  imports: [
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
    MatButtonModule,
    FormsModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatAutocompleteModule,
    HttpClientModule,
    HttpModule,
    BrowserModule,
    RouterModule.forRoot(routes)
  ],
  providers: [
    HttpClient,
    GuardService,
    { provide: OwlDateTimeIntl, useClass: DefaultIntl }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
