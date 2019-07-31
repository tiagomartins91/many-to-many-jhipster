import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AppTestSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [AppTestSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [AppTestSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppTestSharedModule {
  static forRoot() {
    return {
      ngModule: AppTestSharedModule
    };
  }
}
