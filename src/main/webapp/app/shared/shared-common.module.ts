import { NgModule } from '@angular/core';

import { AppTestSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [AppTestSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [AppTestSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class AppTestSharedCommonModule {}
