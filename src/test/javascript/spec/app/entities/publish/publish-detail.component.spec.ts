import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { PublishDetailComponent } from 'app/entities/publish/publish-detail.component';
import { Publish } from 'app/shared/model/publish.model';

describe('Component Tests', () => {
  describe('Publish Management Detail Component', () => {
    let comp: PublishDetailComponent;
    let fixture: ComponentFixture<PublishDetailComponent>;
    const route = ({ data: of({ publish: new Publish(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [PublishDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PublishDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PublishDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.publish).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
