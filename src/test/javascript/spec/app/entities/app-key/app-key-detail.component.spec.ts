import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AppKeyDetailComponent } from 'app/entities/app-key/app-key-detail.component';
import { AppKey } from 'app/shared/model/app-key.model';

describe('Component Tests', () => {
  describe('AppKey Management Detail Component', () => {
    let comp: AppKeyDetailComponent;
    let fixture: ComponentFixture<AppKeyDetailComponent>;
    const route = ({ data: of({ appKey: new AppKey(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AppKeyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AppKeyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppKeyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.appKey).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
